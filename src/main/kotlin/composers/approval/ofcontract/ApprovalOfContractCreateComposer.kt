package composers.approval.ofcontract

import models.approval.Approval
import models.approval.ApprovalRequestParametersWrapper
import models.approval.ApprovalValidator
import models.approval.factories.ApprovalFactories
import orm.approvalgeneratedrepository.ApprovalToJsonSerializer
import orm.services.ModelInvalidError
import orm.utils.TransactionRunner
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class ApprovalOfContractCreateComposer(val contractId: Long?, val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (Approval)->Unit
    lateinit var onError: (Approval)->Unit

    lateinit var approvalToCreate: Approval
    lateinit var wrappedParams: ApprovalRequestParametersWrapper

    override fun beforeCompose(){
        contractId ?: throw BadRequestError("no contract id in routeParams")
        wrapParams()
        build()
        validate()
    }

    private fun wrapParams() {
        params.get("approval")?.let {
            wrappedParams = ApprovalRequestParametersWrapper(it)
        } ?: throw BadRequestError("invalid params schema")
    }

    private fun build() {
        approvalToCreate = ApprovalFactories.ofContractDefault.create(wrappedParams)
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
                approvalStep.approvalId = approvalToCreate.id
                approvalStep.record.save(tx)

                approvalStep.approvalStepToApproverLinks?.forEach {approvalStepToApproverLink ->
                    approvalStepToApproverLink.approvalStepId = approvalStep.id
                    approvalStepToApproverLink.record.save(tx)
                }

                approvalStep.approvalStepToUploadedDocumentLinks?.forEach {approvalStepToUploadedDocumentLink ->
                    approvalStepToUploadedDocumentLink.approvalStepId = approvalStep.id
                    try {
                        approvalStepToUploadedDocumentLink.uploadedDocument?.record?.save(tx)
                    } finally {
                        approvalStepToUploadedDocumentLink.uploadedDocument?.file?.finalizeOperation()
                    }

                    approvalStepToUploadedDocumentLink.uploadedDocumentId = approvalStepToUploadedDocumentLink.uploadedDocument?.id
                    approvalStepToUploadedDocumentLink.record.save(tx)
                }
            }

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

