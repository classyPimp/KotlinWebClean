package composers.approval.ofcontract

import models.approval.Approval
import models.approval.ApprovalRequestParametersWrapper
import models.approval.ApprovalValidator
import models.approval.factories.ApprovalFactories
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import models.approvaltoapproverlink.ApprovalToApproverLink
import orm.services.ModelInvalidError
import orm.utils.TransactionRunner
import permissionsystem.ApprovalOfContractPermissions
import permissionsystem.CurrentUserUnauthorizedError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.currentuser.ICurrentUser
import utils.requestparameters.IParam
import java.sql.Timestamp
import java.time.Instant

class ApprovalOfContractCreateComposer(
        val contractId: Long?, val params: IParam, val currentUser: ICurrentUser
) : ComposerBase() {

    lateinit var onSuccess: (Approval)->Unit
    lateinit var onError: (Approval)->Unit

    lateinit var approvalToCreate: Approval
    lateinit var wrappedParams: ApprovalRequestParametersWrapper

    override fun beforeCompose(){
        checkIfCurrentUserIsAuthorized()
        contractId ?: throw BadRequestError("no contract id in routeParams")
        wrapParams()
        build()
        validate()
    }

    private fun checkIfCurrentUserIsAuthorized() {
        if (!ApprovalOfContractPermissions.isAuthorizedToCreate(currentUser)) {
            throw CurrentUserUnauthorizedError()
        }
    }

    private fun wrapParams() {
        params.get("approval")?.let {
            wrappedParams = ApprovalRequestParametersWrapper(it)
        } ?: throw BadRequestError("invalid params schema")
    }

    private fun build() {
        approvalToCreate = ApprovalFactories.ofContractDefault.create(wrappedParams)
        handleCurrentUserInApproverLinks()

    }

    private fun handleCurrentUserInApproverLinks() {
        approvalToCreate.approvalToApproverLinks = approvalToCreate.approvalToApproverLinks
            ?: mutableListOf()
        approvalToCreate.approvalSteps!!.let {
            val approvalStep = it.getOrNull(0)
            if (approvalStep != null) {
                approvalStep.approvalStepToApproverLinks = approvalStep.approvalStepToApproverLinks
                    ?: mutableListOf()
            }
        }

        val approvalToApproverLinks = approvalToCreate.approvalToApproverLinks!!
        val approvalStepToApproverLinks = approvalToCreate.approvalSteps!!.get(0).approvalStepToApproverLinks!!

        val currentUserId = currentUser.userModel!!.id!!

        val approvalToApproverLinkWithCurrentUser  = approvalToApproverLinks.firstOrNull { it.userId == currentUserId }
                ?: ApprovalToApproverLink().also {
                    approvalToApproverLinks.add(it)
                }
        val approvalStepToApproverLinkWithCurrentUser = approvalStepToApproverLinks.firstOrNull { it.userId == currentUserId }
                ?: ApprovalStepToApproverLink().also {
                    approvalStepToApproverLinks.add(it)
                }

        approvalToApproverLinkWithCurrentUser.also {
            it.userId = currentUser.userModel!!.id!!
            it.isApproved = Timestamp(Instant.now().toEpochMilli())
        }
        approvalStepToApproverLinkWithCurrentUser.also {
            it.userId = currentUser.userModel!!.id!!
            it.isApproved = Timestamp(Instant.now().toEpochMilli())
        }
    }

    private fun validate() {
        ApprovalValidator(approvalToCreate).ofContractCreateScenario()
        if (!approvalToCreate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        TransactionRunner.run {
            val tx = it.inTransactionDsl
            approvalToCreate.record.save(tx)

            approvalToCreate.approvalSteps?.forEach {approvalStep ->

                approvalStep.approvalStepToUploadedDocumentLinks?.forEach {approvalStepToUploadedDocumentLink ->
                    try {
                        approvalStepToUploadedDocumentLink.uploadedDocument?.record?.save(tx)
                    } finally {
                        approvalStepToUploadedDocumentLink.uploadedDocument?.file?.finalizeOperation()
                    }
                    approvalStepToUploadedDocumentLink.uploadedDocumentId = approvalStepToUploadedDocumentLink.uploadedDocument?.id
                }

                approvalStep.approvalId = approvalToCreate.id
                approvalStep.record.save(tx)

                approvalStep.approvalStepToUploadedDocumentLinks?.forEach {approvalStepToUploadedDocumentLink ->
                    approvalStepToUploadedDocumentLink.approvalStepId = approvalStep.id
                    approvalStepToUploadedDocumentLink.record.save(tx)
                }

                approvalStep.approvalStepToApproverLinks?.forEach {approvalStepToUploadedDocumentLink ->
                    approvalStepToUploadedDocumentLink.approvalStepId = approvalStep.id
                    approvalStepToUploadedDocumentLink.record.save(tx)
                }
            }

            approvalToCreate.record.lastStageId = approvalToCreate.approvalSteps?.lastOrNull()?.id!!
            approvalToCreate.record.save(tx)

            approvalToCreate.approvalToApproverLinks?.forEach {approvalToApproverLink ->
                approvalToApproverLink.approvalId = approvalToCreate.id
                approvalToApproverLink.record.save(tx)
            }
        }
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(approvalToCreate)
            }
            else -> {
                throw error
            }
        }
    }


    override fun success() {
        onSuccess.invoke(approvalToCreate)
    }

}

