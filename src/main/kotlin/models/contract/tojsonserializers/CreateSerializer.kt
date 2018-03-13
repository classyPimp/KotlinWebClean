package models.contract.tojsonserializers

import models.contract.Contract
import orm.contractgeneratedrepository.ContractToJsonSerializer

object CreateSerializer {

    fun onSuccess(contract: Contract): String {
        ContractToJsonSerializer(contract).let {
            it.includeContractCategory()
            it.includeContractToCounterPartyLinks() {
                it.includeCounterParty() {
                    it.includeIncorporationForm()
                }
            }
            it.includeContractNumber()
            it.includeContractStatus()
            return it.serializeToString()
        }
    }

    fun onError(contract: Contract): String {
        ContractToJsonSerializer(contract). let {
            it.includeContractCategory() {
                it.includeErrors()
            }
            it.includeContractToCounterPartyLinks() {
                it.includeErrors()
                it.includeCounterParty() {
                    it.includeIncorporationForm()
                    it.includeErrors()
                }
            }
            it.includeContractStatus() {
                it.includeErrors()
            }
            it.includeContractNumber() {
                it.includeErrors()
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}