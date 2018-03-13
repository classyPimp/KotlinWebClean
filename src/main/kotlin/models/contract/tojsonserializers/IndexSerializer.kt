package models.contract.tojsonserializers

import models.contract.Contract
import orm.contractgeneratedrepository.ContractToJsonSerializer

object IndexSerializer {

    fun onSuccess(contracts: MutableList<Contract>): String {
        return ContractToJsonSerializer.serialize(contracts) {
            it.includeContractCategory()
            it.includeContractStatus()
            it.includeContractNumber()
            it.includeContractToCounterPartyLinks() {
                it.includeCounterParty() {
                    it.includeIncorporationForm()
                }
            }
        }.toString()
    }


}