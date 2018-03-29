package models.approvalsteptouploadeddocumentlink

import models.approvalsteptouploadeddocumentlink.ApprovalStepToUploadedDocumentLink
import models.uploadeddocument.UploadedDocumentRequestParametersWrapper
import utils.requestparameters.IParam

class ApprovalStepToUploadedDocumentLinkRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long() }
    val approvalStepId: Long? by lazy { requestParameters.get("approvalStepId")?.long() }
    val description: String? by lazy { requestParameters.get("description")?.string }
    val uploadedDocumentId: Long? by lazy { requestParameters.get("uploadedDocumentId")?.long() }
    val uploadedDocument: UploadedDocumentRequestParametersWrapper? by lazy {
        requestParameters.get("uploadedDocument")?.let {
            UploadedDocumentRequestParametersWrapper(it)
        }
    }
}