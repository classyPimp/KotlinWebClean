package models.contracttouploadeddocumentlink.factories

import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLinkRequestParametersWrapper
import models.uploadeddocument.UploadedDocument
import models.uploadeddocument.factories.UploadedDocumentFactories

object DefaultFactory {

    fun create(params: ContractToUploadedDocumentLinkRequestParametersWrapper): ContractToUploadedDocumentLink {
        return ContractToUploadedDocumentLink().also {
            it.contractToUploadedDocumentLinkReasonId = params.contractToUploadedDocumentLinkReasonId
            it.uploadedDocument = params.uploadedDocument?.let {
                UploadedDocumentFactories.defaultCreate.create(it)
            }
            it.description = params.description
        }
    }

}