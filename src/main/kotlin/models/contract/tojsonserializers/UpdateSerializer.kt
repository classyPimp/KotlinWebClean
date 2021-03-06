package models.contract.tojsonserializers

import models.contract.Contract
import orm.contractgeneratedrepository.ContractToJsonSerializer

object UpdateSerializer {

    fun onSuccess(contract: Contract): String {
        ContractToJsonSerializer(contract).let {
            it.includeContractCategory()
            return it.serializeToString()
        }
    }

    fun onError(contract: Contract): String {
        ContractToJsonSerializer(contract). let {
            it.includeContractCategory()
            it.includeErrors()
            return it.serializeToString()
        }
    }

}