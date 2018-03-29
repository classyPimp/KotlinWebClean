package models.approvalrejectiontouploadeddocumentlink

import models.approvalrejectiontouploadeddocumentlink.ApprovalRejectionToUploadedDocumentLink
import models.uploadeddocument.UploadedDocumentRequestParametersWrapper
import utils.requestparameters.IParam

class ApprovalRejectionToUploadedDocumentLinkRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long() }
    val approvalRejectionId: Long? by lazy { requestParameters.get("approvalRejectionId")?.long() }
    val uploadedDocumentId: Long? by lazy { requestParameters.get("uploadedDocumentId")?.long() }
    val uploadedDocument: UploadedDocumentRequestParametersWrapper? by lazy {
        requestParameters.get("uploadedDocument")?.let {
            UploadedDocumentRequestParametersWrapper(it)
        }
    }
}