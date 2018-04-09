package composers.approvalrejection

import models.approval.Approval
import models.approval.daos.ApprovalDaos
import utils.composer.ComposerBase
import models.approvalrejection.ApprovalRejection
import models.approvalrejection.ApprovalRejectionRequestParametersWrapper
import models.approvalrejection.ApprovalRejectionValidator
import models.approvalrejection.factories.ofapprovalsteptoapproverlinkofcontract.ApprovalRejectionOfApprovalStepToApproverLinkOfContractFactory
import models.approvalsteptoapproverlink.daos.ApprovalStepToApproverLinkDaos
import models.approvaltoapproverlink.daos.ApprovalToApproverLinkDaos
import models.discussion.Discussion
import org.jooq.DSLContext
import orm.approvalrejectiontouploadeddocumentlinkgeneratedrepository.ApprovalRejectionToUploadedDocumentLinkDefaultAssociationsManager
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import orm.utils.TransactionRunner
import permissionsystem.CurrentUserUnauthorizedError
import utils.composer.composerexceptions.BadRequestError
import utils.currentuser.ICurrentUser
import utils.requestparameters.IParam

class ApprovalRejectionOfApprovalStepToApprovalLinkOfContractCreateComposer(
        val approvalId: Long?,
        val params: IParam,
        val currentUser: ICurrentUser
) : ComposerBase() {

    lateinit var onSuccess: (ApprovalRejection)->Unit
    lateinit var onError: (ApprovalRejection)->Unit

    lateinit var approvalRejectionToCreate: ApprovalRejection
    lateinit var approval: Approval
    lateinit var wrappedParams: ApprovalRejectionRequestParametersWrapper

    override fun beforeCompose(){
        approvalId ?: failImmediately(BadRequestError())
        finAndSetApproval()
        checkIfUserIsAuthorized()
        wrapParams()
        build()
        validate()
    }

    private fun finAndSetApproval() {
        ApprovalDaos.show.findForApprovalRejectionOfContractCreate(approvalId!!)?.let {
            approval = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun checkIfUserIsAuthorized() {
        if (!currentUser.isLoggedIn()) {
            failImmediately(CurrentUserUnauthorizedError())
        }
        ApprovalToApproverLinkDaos.show.existsForUserAndApproval(
                userId = currentUser.userModel!!.id!!,
                approvalId = approval.id!!
        ) ?: failImmediately(CurrentUserUnauthorizedError())

    }

    private fun wrapParams() {
        params.get("approvalRejection")?.let {
            wrappedParams = ApprovalRejectionRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun build() {
        approvalRejectionToCreate = ApprovalRejectionOfApprovalStepToApproverLinkOfContractFactory.default(
                params = wrappedParams,
                approvalId = approvalId!!,
                userId = currentUser.userModel!!.id!!
        )
    }

    private fun validate() {
        ApprovalRejectionValidator(approvalRejectionToCreate).ofContractCreateScenario()
        if (!approvalRejectionToCreate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        TransactionRunner.run {
            val tx = it.inTransactionDsl

            approvalRejectionToCreate.approvalRejectionToUploadedDocumentLinks?.forEach {
                it.uploadedDocument?.record?.save(tx)
                ApprovalRejectionToUploadedDocumentLinkDefaultAssociationsManager.copyForeignKeyFromContainedUploadedDocument(it)
            }

            approvalRejectionToCreate.record.saveCascade(tx)

            approvalRejectionToCreate.approvalRejectionToUploadedDocumentLinks?.forEach {
                it.uploadedDocument?.file?.finalizeOperation()
            }

            createDiscussion(tx)
        }
    }

    private fun createDiscussion(tx: DSLContext) {
        val discussion = Discussion().also {
            it.userId = currentUser.userModel!!.id!!
            it.topic = approvalRejectionToCreate.reasonText
            it.discussableId = approvalRejectionToCreate.id!!
            it.discussableType = "ApprovalRejection"
            it.record.save(tx)
        }
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(approvalRejectionToCreate)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(approvalRejectionToCreate)
    }

}

