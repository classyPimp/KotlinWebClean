package models.contracttocounterpartylink.tojsonserializers.forcontract

import models.contracttocounterpartylink.ContractToCounterPartyLink
import orm.contracttocounterpartylinkgeneratedrepository.ContractToCounterPartyLinkToJsonSerializer

object ForContractReplaceSerializer {

    fun onSuccess(contractToCounterPartyLink: ContractToCounterPartyLink): String {
        ContractToCounterPartyLinkToJsonSerializer(contractToCounterPartyLink).let {
            it.includeCounterParty() {
                it.includeIncorporationForm()
            }
            return it.serializeToString()
        }
    }

    fun onError(contractToCounterPartyLink: ContractToCounterPartyLink): String {
        ContractToCounterPartyLinkToJsonSerializer(contractToCounterPartyLink). let {
            it.includeCounterParty() {
                it.includeErrors()
                it.includeIncorporationForm() {
                    it.includeErrors()
                }
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}