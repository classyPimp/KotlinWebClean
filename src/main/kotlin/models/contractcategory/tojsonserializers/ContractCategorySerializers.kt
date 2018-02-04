package models.contractcategory.tojsonserializers

object ContractCategorySerializers {

    object FormFeeds {
        val index = FormFeedsIndex
    }

    val create = CreateSerializer
    val destroy = DestroySerializer
    val edit = EditSerializer
    val index = IndexSerializer
    val show = ShowSerializer
    val update = UpdateSerializer

}