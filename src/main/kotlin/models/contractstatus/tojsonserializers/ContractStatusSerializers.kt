package models.contractstatus.tojsonserializers

import models.contractstatus.tojsonserializers.forcontract.ContractStatusEditForContractToJsonSerializer
import models.contractstatus.tojsonserializers.forcontract.ContractStatusForContractShowSerializer
import models.contractstatus.tojsonserializers.forcontract.ContractStatusUpdateForContractToJsonSerializer

object ContractStatusSerializers {

    object ForContract {
        val edit = ContractStatusEditForContractToJsonSerializer
        val update = ContractStatusUpdateForContractToJsonSerializer
        val show = ContractStatusForContractShowSerializer
    }

}