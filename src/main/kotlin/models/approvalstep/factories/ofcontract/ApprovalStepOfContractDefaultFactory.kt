package models.approvalstep.factories.ofcontract

import models.approval.Approval
import models.approvalstep.ApprovalStep
import models.approvalstep.ApprovalStepRequestParametersWrapper
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import models.approvalsteptouploadeddocumentlink.factories.ApprovalStepToUploadedDocumentLinkFactories
import models.approvaltoapproverlink.ApprovalToApproverLink

object ApprovalStepOfContractDefaultFactory {

    fun create(params: ApprovalStepRequestParametersWrapper, approvalToApproverLinks: MutableList<ApprovalToApproverLink>?): ApprovalStep {
        return ApprovalStep().also {model ->
            val approvalStepToApproverLinks = mutableListOf<ApprovalStepToApproverLink>()
            approvalToApproverLinks?.forEach {approvalToApproverLink ->
                approvalStepToApproverLinks.add(
                        ApprovalStepToApproverLink().also {approvalStepToApproverLink ->
                            approvalStepToApproverLink.userId = approvalToApproverLink.userId
                        }
                )
            }
            model.approvalStepToApproverLinks = approvalStepToApproverLinks

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