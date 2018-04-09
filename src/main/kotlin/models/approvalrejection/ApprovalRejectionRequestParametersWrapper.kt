package models.approvalrejection

import models.approvalrejection.ApprovalRejection
import models.approvalrejectiontouploadeddocumentlink.ApprovalRejectionToUploadedDocumentLink
import models.approvalrejectiontouploadeddocumentlink.ApprovalRejectionToUploadedDocumentLinkRequestParametersWrapper
import utils.requestparameters.IParam
import utils.stdlibextensions.string.trimAndSquishWhiteSpace
import java.sql.Timestamp

class ApprovalRejectionRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long() }
    val approvalId: Long? by lazy { requestParameters.get("approvalId")?.long() }
    val reasonText: String? by lazy { requestParameters.get("reasonText")?.string?.trimAndSquishWhiteSpace() }
    val fullfilled: Timestamp? by lazy { requestParameters.get("isFullfilled")?.timestamp }
    val approvalRejectionToUploadedDocumentLinks: MutableList<ApprovalRejectionToUploadedDocumentLinkRequestParametersWrapper>?
        by lazy {
            requestParameters.get("approvalRejectionToUploadedDocumentLinks")?.paramList()?.mapTo(
                mutableListOf<ApprovalRejectionToUploadedDocumentLinkRequestParametersWrapper>()
            ) {
                ApprovalRejectionToUploadedDocumentLinkRequestParametersWrapper(it)
            }
        }


}