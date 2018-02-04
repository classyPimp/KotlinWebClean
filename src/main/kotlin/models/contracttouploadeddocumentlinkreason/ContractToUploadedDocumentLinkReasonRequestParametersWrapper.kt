package models.contracttouploadeddocumentlinkreason

import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason
import utils.requestparameters.IParam
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

class ContractToUploadedDocumentLinkReasonRequestParametersWrapper(val requestParameters: IParam) {

    val name: String? by lazy { requestParameters.get("name")?.string?.trimAndSquishWhiteSpace() }
    val description: String? by lazy { requestParameters.get("description")?.string?.trimAndSquishWhiteSpace() }

}