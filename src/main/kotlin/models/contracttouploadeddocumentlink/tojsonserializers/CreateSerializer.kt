package models.contracttouploadeddocumentlink.tojsonserializers

import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import orm.contracttouploadeddocumentlinkgeneratedrepository.ContractToUploadedDocumentLinkToJsonSerializer

object CreateSerializer {

    fun onSuccess(contractToUploadedDocumentLink: ContractToUploadedDocumentLink): String {
        ContractToUploadedDocumentLinkToJsonSerializer(contractToUploadedDocumentLink).let {
            it.includeContractToUploadedDocumentLinkReason()
            it.includeUploadedDocument()
            return it.serializeToString()
        }
    }

    fun onError(contractToUploadedDocumentLink: ContractToUploadedDocumentLink): String {
        ContractToUploadedDocumentLinkToJsonSerializer(contractToUploadedDocumentLink). let {
            it.includeContractToUploadedDocumentLinkReason() {
                it.includeErrors()
            }
            it.includeUploadedDocument() {
                it.includeErrors()
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}