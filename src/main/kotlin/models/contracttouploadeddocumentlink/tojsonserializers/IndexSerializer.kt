package models.contracttouploadeddocumentlink.tojsonserializers

import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import orm.contracttouploadeddocumentlinkgeneratedrepository.ContractToUploadedDocumentLinkToJsonSerializer

object IndexSerializer {

    fun onSuccess(contractToUploadedDocumentLinks: MutableList<ContractToUploadedDocumentLink>): String {
        return ContractToUploadedDocumentLinkToJsonSerializer.serialize(contractToUploadedDocumentLinks)
                .toString()
    }

}