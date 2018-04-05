package models.approvalrejection.factories.ofapprovalsteptoapproverlinkofcontract

import models.approvalrejection.ApprovalRejection
import models.approvalrejection.ApprovalRejectionRequestParametersWrapper
import models.approvalrejectiontouploadeddocumentlink.ApprovalRejectionToUploadedDocumentLink
import models.approvalrejectiontouploadeddocumentlink.factories.approvalrejectiontouploadeddocumentlink.ApprovalRejectionToUploadedDocumentLinkFactory

object ApprovalRejectionOfApprovalStepToApproverLinkOfContractFactory {
    fun default(params: ApprovalRejectionRequestParametersWrapper, approvalStepToApproverLinkId: Long): ApprovalRejection {
        return  ApprovalRejection().also {
            it.approvalStepToApproverLinkId = approvalStepToApproverLinkId
            it.reasonText = params.reasonText
            it.approvalRejectionToUploadedDocumentLinks = params.approvalRejectionToUploadedDocumentLinks?.let {
                it.mapTo(mutableListOf<ApprovalRejectionToUploadedDocumentLink>()) {
                    ApprovalRejectionToUploadedDocumentLinkFactory.ofContractDefault(it)
                }
            }
        }
    }

}