package models.contractstatus.tojsonserializers.forcontract

import models.contractstatus.ContractStatus
import orm.contractstatusgeneratedrepository.ContractStatusToJsonSerializer

object ContractStatusForContractShowSerializer {

    fun onSuccess(contractStatus: ContractStatus): String {
        ContractStatusToJsonSerializer(contractStatus).let {

            return it.serializeToString()
        }
    }

    fun onError(contractStatus: ContractStatus): String {
        ContractStatusToJsonSerializer(contractStatus). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}