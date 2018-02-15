package models.contracttouploadeddocumentlink.updaters

import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLinkRequestParametersWrapper


object Default {

    fun update(model: ContractToUploadedDocumentLink, params: ContractToUploadedDocumentLinkRequestParametersWrapper) {
        model.record.let {
            it.description = params.description
        }
    }

}