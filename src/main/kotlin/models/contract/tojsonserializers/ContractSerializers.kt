package models.contract.tojsonserializers

import models.contract.tojsonserializers.manage.ManageEditSerializer
import models.contractstatus.tojsonserializers.forcontract.ContractStatusForContractShowSerializer

object ContractSerializers {

    object Manage {
        val edit = ManageEditSerializer
    }

    object ForContract {
        val show = ContractStatusForContractShowSerializer
    }

    val create = CreateSerializer
    val edit = EditSerializer
    val index = IndexSerializer
    val show = ShowSerializer
    val update = UpdateSerializer
    val updateGeneralInfo = ContractUpdateGeneralInfoToJsonSerializer

}