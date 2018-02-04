package models.contracttouploadeddocumentlinkreason.tojsonserializers

object ContractToUploadedDocumentLinkReasonSerializers {

    object FormFeeds {
        val index = FormFeedsIndex
    }

    val create = CreateSerializer
    val show = ShowSerializer
    val index = IndexSerializer
    val destroy = DestroySerializer
    val update = UpdateSerializer
    val edit = EditSerializer

}