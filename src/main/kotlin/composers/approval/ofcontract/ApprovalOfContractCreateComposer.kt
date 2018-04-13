package composers.approval.ofcontract

import models.approval.Approval
import models.approval.ApprovalRequestParametersWrapper
import models.approval.ApprovalValidator
import models.approval.factories.ApprovalFactories
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import models.contract.Contract
import models.contract.daos.ContractDaos
import orm.modelUtils.exceptions.ModelNotFoundError
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
    lateinit var contract: Contract

    override fun beforeCompose(){
        checkIfCurrentUserIsAuthorized()
        contractId ?: throw BadRequestError("no contract id in routeParams")
        findAndSetContractPreloadingRequired()
        checkIfContractApprovedOrApprovalIsPendingAndThrowIfSo()
        wrapParams()
        build()
        updateContractStatus()
        validate()
    }

    private fun checkIfCurrentUserIsAuthorized() {
        if (!ApprovalOfContractPermissions.isAuthorizedToCreate(currentUser)) {
            throw CurrentUserUnauthorizedError()
        }
    }

    private fun findAndSetContractPreloadingRequired() {
        ContractDaos.show.byIdPreloadingContractStatus(contractId!!)?.let {
            contract = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun checkIfContractApprovedOrApprovalIsPendingAndThrowIfSo() {
        contract.contractStatus!!.let {
            if (it.pendingApproval != null) {
                throw IllegalStateException()
            }
            if (it.isApproved != null) {
                throw IllegalStateException()
            }
        }
    }

    private fun wrapParams() {
        params.get("approval")?.let {
            wrappedParams = ApprovalRequestParametersWrapper(it)
        } ?: throw BadRequestError("invalid params schema")
    }

    private fun build() {
        approvalToCreate = ApprovalFactories.ofContractDefault.create(wrappedParams, contractId!!)
        handleCurrentUserInApproverLinks()
    }

    private fun updateContractStatus() {
        contract.contractStatus!!.record.pendingApproval = Timestamp(Instant.now().toEpochMilli())
    }

    private fun handleCurrentUserInApproverLinks() {
        val approvalStep = approvalToCreate.approvalSteps!!.first()

        approvalStep.approvalStepToApproverLinks = approvalStep.approvalStepToApproverLinks ?: mutableListOf()

        val approvalStepToApproverLinks = approvalStep.approvalStepToApproverLinks!!

        val currentUserId = currentUser.userModel!!.id!!

        val approvalStepToApproverLinkWithCurrentUser = approvalStepToApproverLinks.find { it.userId == currentUserId }
                ?: ApprovalStepToApproverLink().also {
                    approvalStepToApproverLinks.add(it)
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

            contract.contractStatus!!.record.save(tx)

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

