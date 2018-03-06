package models.contracttouploadeddocumentlink.tojsonserializers.ofcontract

import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import orm.contracttouploadeddocumentlinkgeneratedrepository.ContractToUploadedDocumentLinkToJsonSerializer

object ContractToUploadedDocumentlinkOfContractIndexEditJsonSerializer {

    fun onSuccess(contractToUploadedDocumentLinks: MutableList<ContractToUploadedDocumentLink>): String {
        return ContractToUploadedDocumentLinkToJsonSerializer.serialize(contractToUploadedDocumentLinks) {
            it.includeContractToUploadedDocumentLinkReason()
            it.includeUploadedDocument()
        }.toString()
    }

}