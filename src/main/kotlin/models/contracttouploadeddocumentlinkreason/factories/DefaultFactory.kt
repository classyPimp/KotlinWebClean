package models.contracttouploadeddocumentlinkreason.factories

import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason
import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReasonRequestParametersWrapper

object DefaultFactory {

    fun create(params: ContractToUploadedDocumentLinkReasonRequestParametersWrapper): ContractToUploadedDocumentLinkReason {
        return ContractToUploadedDocumentLinkReason().also {
            it.name = params.name
            it.description = params.description
        }
    }

}