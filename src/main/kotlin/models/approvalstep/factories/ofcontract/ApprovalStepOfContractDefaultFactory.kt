package models.approvalstep.factories.ofcontract

import models.approvalstep.ApprovalStep
import models.approvalstep.ApprovalStepRequestParametersWrapper
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import models.approvalsteptouploadeddocumentlink.ApprovalStepToUploadedDocumentLink
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

}