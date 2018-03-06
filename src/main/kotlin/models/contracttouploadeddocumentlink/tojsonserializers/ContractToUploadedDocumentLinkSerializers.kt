package models.contracttouploadeddocumentlink.tojsonserializers

import models.contracttouploadeddocumentlink.tojsonserializers.ofcontract.ContractToUploadedDocumentlinkOfContractIndexEditJsonSerializer

object ContractToUploadedDocumentLinkSerializers {

    val create = CreateSerializer
    val update = UpdateSerializer
    val destroy = DestroySerializer
    val index = IndexSerializer
    val show = ShowSerializer

    object OfContract {
        val indexEdit = ContractToUploadedDocumentlinkOfContractIndexEditJsonSerializer
    }

}