package models.contracttouploadeddocumentlinkreason.tojsonserializers

import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason
import orm.contracttouploadeddocumentlinkreasongeneratedrepository.ContractToUploadedDocumentLinkReasonToJsonSerializer

object UpdateSerializer {

    fun onSuccess(contractToUploadedDocumentLinkReason: ContractToUploadedDocumentLinkReason): String {
        ContractToUploadedDocumentLinkReasonToJsonSerializer(contractToUploadedDocumentLinkReason).let {

            return it.serializeToString()
        }
    }

    fun onError(contractToUploadedDocumentLinkReason: ContractToUploadedDocumentLinkReason): String {
        ContractToUploadedDocumentLinkReasonToJsonSerializer(contractToUploadedDocumentLinkReason). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}