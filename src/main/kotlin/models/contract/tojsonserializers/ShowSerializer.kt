package models.contract.tojsonserializers

import models.contract.Contract
import orm.contractgeneratedrepository.ContractToJsonSerializer

object ShowSerializer {

    fun onSuccess(contract: Contract): String {
        ContractToJsonSerializer(contract).let {
                it.includeContractCategory()
                it.includeContractToCounterPartyLinks() {
                    it.includeCounterParty() {
                        it.includeIncorporationForm()
                    }
                }
                it.includeContractStatus()
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