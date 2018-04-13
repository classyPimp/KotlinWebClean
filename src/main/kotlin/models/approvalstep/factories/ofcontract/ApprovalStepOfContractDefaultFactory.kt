package models.approvalstep.factories.ofcontract

import models.approval.Approval
import models.approvalstep.ApprovalStep
import models.approvalstep.ApprovalStepRequestParametersWrapper
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import models.approvalsteptouploadeddocumentlink.factories.ApprovalStepToUploadedDocumentLinkFactories

object ApprovalStepOfContractDefaultFactory {

    fun create(params: ApprovalStepRequestParametersWrapper): ApprovalStep {
        return ApprovalStep().also {model ->
            model.approvalStepToApproverLinks = params.approvalStepToApproverLinks?.mapTo(mutableListOf()) {
                ApprovalStepToApproverLink().also {approvalStepToApproverLink ->
                    approvalStepToApproverLink.userId = it.userId
                }
            }
            model.approvalStepToUploadedDocumentLinks = params.approvalStepToUploadedDocumentLinks?.let {
                ApprovalStepToUploadedDocumentLinkFactories.OfContract.default.createList(it)
            }
        }
    }

    fun createWhenAfterFirstStep(params: ApprovalStepRequestParametersWrapper, approval: Approval): ApprovalStep {
        return ApprovalStep().also {
            it.approvalId = approval.id!!
            //when loading approval for approvalStep Create, only last step supposed to be loaded.
            val lastStep = approval.approvalSteps!!.last()
            copyApprovalStepToApproverLinks(it, lastStep)
            it.approvalStepToUploadedDocumentLinks = params.approvalStepToUploadedDocumentLinks?.let {
                ApprovalStepToUploadedDocumentLinkFactories.OfContract.default.createList(it)
            }

        }
    }


    private fun copyApprovalStepToApproverLinks(approvalStepToCloneTo: ApprovalStep, sourceApprovalStep: ApprovalStep) {
        val clonedApprovalStepToApproverLinks = sourceApprovalStep.approvalStepToApproverLinks!!.mapTo(mutableListOf<ApprovalStepToApproverLink>()) { approvalStepToBeCloned ->
            ApprovalStepToApproverLink().also {
                it.userId = approvalStepToBeCloned.userId
            }
        }
        approvalStepToCloneTo.approvalStepToApproverLinks = clonedApprovalStepToApproverLinks
    }

}