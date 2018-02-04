package models.contracttouploadeddocumentlinkreason.updaters

import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason
import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReasonRequestParametersWrapper


object DefaultUpdater {

    fun update(model: ContractToUploadedDocumentLinkReason, params: ContractToUploadedDocumentLinkReasonRequestParametersWrapper) {
        model.record.let {
            it.name = params.name
            it.description = params.description
        }
    }

}