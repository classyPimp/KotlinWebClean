package composers.approvalstep

import models.approval.Approval
import models.approval.daos.ApprovalDaos
import models.approvalrejection.factories.ApprovalRejectionFactories
import utils.composer.ComposerBase
import models.approvalstep.ApprovalStep
import models.approvalstep.ApprovalStepRequestParametersWrapper
import models.approvalstep.ApprovalStepValidator
import models.approvalstep.factories.ApprovalStepFactories
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import orm.utils.TransactionRunner
import permissionsystem.CurrentUserUnauthorizedError
import utils.composer.composerexceptions.BadRequestError
import utils.currentuser.ICurrentUser
import utils.requestparameters.IParam
import java.sql.Timestamp
import java.time.Instant

class ApprovalStepOfApprovalOfContractCreateComposer(
        val approvalId: Long?,
        val currentUser: ICurrentUser,
        val params: IParam
) : ComposerBase() {

    lateinit var onSuccess: (ApprovalStep)->Unit
    lateinit var onError: (ApprovalStep)->Unit

    lateinit var approvalStepToCreate: ApprovalStep
    lateinit var approval: Approval
    lateinit var wrappedParams: ApprovalStepRequestParametersWrapper

    override fun beforeCompose(){
        approvalId ?: failImmediately(BadRequestError())
        findAndSetApprovalPreloadingRequired()
        checkIfUserIsAuthorized()
        wrapParams()
        build()
        validate()
    }

    private fun findAndSetApprovalPreloadingRequired() {
        ApprovalDaos.show.ofContractForApprovalStepCreate(approvalId!!)?.let {
            approval = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun checkIfUserIsAuthorized() {
        if (!currentUser.isLoggedIn()) {
            failImmediately(CurrentUserUnauthorizedError())
        }
        approval.approvalToApproverLinks!!.find {
            it.userId == currentUser.userModel!!.id!!
        } ?: failImmediately(CurrentUserUnauthorizedError())
    }

    private fun wrapParams() {
        params.get("approvalStep")?.let {
            wrappedParams = ApprovalStepRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun build() {
        approvalStepToCreate = ApprovalStepFactories.OfContract.default.createWhenAfterFirstStep(wrappedParams, approval)
        approvalStepToCreate.approvalStepToApproverLinks?.find {
            it.userId == currentUser.userModel!!.id
        }?.let {
                    it.isApproved = Timestamp(Instant.now().toEpochMilli())
        }
    }

    private fun validate() {
        ApprovalStepValidator(approvalStepToCreate).ofContractCreateAfterFirstStep(approvalStepToCreate, approval)
    }


    override fun compose(){
        TransactionRunner.run {
            val tx = it.inTransactionDsl

            approvalStepToCreate.approvalStepToUploadedDocumentLinks?.forEach { approvalStepToUploadedDocumentLink ->
                approvalStepToUploadedDocumentLink.uploadedDocument?.let {
                    try {
                        it.record.save(tx)
                    } finally {
                        approvalStepToUploadedDocumentLink.uploadedDocument?.file?.finalizeOperation()
                    }
                    approvalStepToUploadedDocumentLink.uploadedDocumentId = it.id
                }
            }
            approvalStepToCreate.record.saveCascade(tx)

            approval.record.lastStageId = approvalStepToCreate.id
            approval.record.save(tx)
        }
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(approvalStepToCreate)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(approvalStepToCreate)
    }

}

