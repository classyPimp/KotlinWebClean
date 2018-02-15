package models.contracttouploadeddocumentlink

import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import models.uploadeddocument.UploadedDocument
import models.uploadeddocument.UploadedDocumentRequestParametersWrapper
import utils.requestparameters.IParam
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

class ContractToUploadedDocumentLinkRequestParametersWrapper(val requestParameters: IParam) {

    val contractId: Long? by lazy { requestParameters.get("contractId")?.long() }
    val contractToUploadedDocumentLinkReasonId: Long? by lazy { requestParameters.get("contractToUploadedDocumentLinkReasonId")?.long() }
    val uploadedDocument by lazy { requestParameters.get("uploadedDocument")?.let {
        UploadedDocumentRequestParametersWrapper(it)
    }}
    val uploadedDocumentId: Long? by lazy { requestParameters.get("uploadedDocumentId")?.long() }
    val description: String? by lazy { requestParameters.get("description")?.string?.trimAndSquishWhiteSpace() }
}