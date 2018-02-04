package models.contracttouploadeddocumentlinkreason.tojsonserializers

import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason
import orm.contracttouploadeddocumentlinkreasongeneratedrepository.ContractToUploadedDocumentLinkReasonToJsonSerializer

object FormFeedsIndex {

    fun onSuccess(contractToUploadedDocumentLinkReasons: MutableList<ContractToUploadedDocumentLinkReason>): String {
        return ContractToUploadedDocumentLinkReasonToJsonSerializer.serialize(contractToUploadedDocumentLinkReasons).toString()
    }


}