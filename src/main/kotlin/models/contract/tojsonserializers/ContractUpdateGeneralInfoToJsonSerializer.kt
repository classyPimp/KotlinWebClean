package models.contract.tojsonserializers

import models.contract.Contract
import orm.contractgeneratedrepository.ContractToJsonSerializer

object ContractUpdateGeneralInfoToJsonSerializer {

    fun onSuccess(contract: Contract): String {
        ContractToJsonSerializer(contract).let {
            it.includeContractCategory()
            it.includeContractStatus()
            return it.serializeToString()
        }
    }

    fun onError(contract: Contract): String {
        ContractToJsonSerializer(contract). let {
            it.includeContractCategory() {
                it.includeErrors()
            }
            it.includeContractStatus() {
                it.includeErrors()
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}