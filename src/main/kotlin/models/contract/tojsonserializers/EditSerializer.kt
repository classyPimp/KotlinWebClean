package models.contract.tojsonserializers

import models.contract.Contract
import orm.contractgeneratedrepository.ContractToJsonSerializer

object EditSerializer {

    fun onSuccess(contract: Contract): String {
        ContractToJsonSerializer(contract).let {

            return it.serializeToString()
        }
    }

    fun onError(contract: Contract): String {
        ContractToJsonSerializer(contract). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}