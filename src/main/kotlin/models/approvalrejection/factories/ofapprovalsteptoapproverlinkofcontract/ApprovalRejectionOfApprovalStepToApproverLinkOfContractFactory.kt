package models.approvalrejection.factories.ofapprovalsteptoapproverlinkofcontract

import models.approvalrejection.ApprovalRejection
import models.approvalrejection.ApprovalRejectionRequestParametersWrapper
import models.approvalrejectiontouploadeddocumentlink.ApprovalRejectionToUploadedDocumentLink
import models.approvalrejectiontouploadeddocumentlink.factories.approvalrejectiontouploadeddocumentlink.ApprovalRejectionToUploadedDocumentLinkFactory

object ApprovalRejectionOfApprovalStepToApproverLinkOfContractFactory {
    fun default(params: ApprovalRejectionRequestParametersWrapper, approvalId: Long, userId: Long): ApprovalRejection {
        return  ApprovalRejection().also {
            it.approvalId = approvalId
            it.reasonText = params.reasonText
            it.approvalRejectionToUploadedDocumentLinks = params.approvalRejectionToUploadedDocumentLinks?.let {
                it.mapTo(mutableListOf<ApprovalRejectionToUploadedDocumentLink>()) {
                    ApprovalRejectionToUploadedDocumentLinkFactory.ofContractDefault(it)
                }
            }
            it.userId = userId
        }
    }

}