package models.contracttouploadeddocumentlink.tojsonserializers

import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import orm.contracttouploadeddocumentlinkgeneratedrepository.ContractToUploadedDocumentLinkToJsonSerializer

/**
 * Created by Муса on 14.02.2018.
 */
object ShowSerializer {

    fun onSuccess(contractToUploadedDocumentLink: ContractToUploadedDocumentLink): String {
        ContractToUploadedDocumentLinkToJsonSerializer(contractToUploadedDocumentLink).let {
            it.includeContractToUploadedDocumentLinkReason()
            return it.serializeToString()
        }
    }

}