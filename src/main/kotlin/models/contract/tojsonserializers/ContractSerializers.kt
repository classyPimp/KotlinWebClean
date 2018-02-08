package models.contract.tojsonserializers

import models.contract.tojsonserializers.manage.ManageEditSerializer

object ContractSerializers {

    object Manage {
        val edit = ManageEditSerializer
    }

    val create = CreateSerializer
    val edit = EditSerializer
    val index = IndexSerializer
    val show = ShowSerializer
    val update = UpdateSerializer

}