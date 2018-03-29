package models.approvalrejection

import models.approvalrejection.ApprovalRejection
import models.approvalrejectiontouploadeddocumentlink.ApprovalRejectionToUploadedDocumentLink
import models.approvalrejectiontouploadeddocumentlink.ApprovalRejectionToUploadedDocumentLinkRequestParametersWrapper
import utils.requestparameters.IParam

class ApprovalRejectionRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long() }
    val approvalStepToApproverLinkId: Long? by lazy { requestParameters.get("approvalStepToApproverLinkId")?.long() }
    val reasonText: String? by lazy { requestParameters.get("reasonText")?.string }
    val approvalRejectionToUploadedDocumentLinks: MutableList<ApprovalRejectionToUploadedDocumentLinkRequestParametersWrapper>?
        by lazy {
            requestParameters.get("approvalRejectionToUploadedDocumentLinks")?.paramList()?.mapTo(
                mutableListOf<ApprovalRejectionToUploadedDocumentLinkRequestParametersWrapper>()
            ) {
                ApprovalRejectionToUploadedDocumentLinkRequestParametersWrapper(it)
            }
        }


}