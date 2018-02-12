package models.contracttouploadeddocumentlink.tojsonserializers

import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import orm.contracttouploadeddocumentlinkgeneratedrepository.ContractToUploadedDocumentLinkToJsonSerializer

object IndexSerializer {

    fun onSuccess(contractToUploadedDocumentLink: ContractToUploadedDocumentLink): String {
        ContractToUploadedDocumentLinkToJsonSerializer(contractToUploadedDocumentLink).let {

            return it.serializeToString()
        }
    }

    fun onError(contractToUploadedDocumentLink: ContractToUploadedDocumentLink): String {
        ContractToUploadedDocumentLinkToJsonSerializer(contractToUploadedDocumentLink). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}