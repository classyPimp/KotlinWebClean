package models.contracttocounterpartylink.tojsonserializers.forcontract

import models.contracttocounterpartylink.ContractToCounterPartyLink
import orm.contracttocounterpartylinkgeneratedrepository.ContractToCounterPartyLinkToJsonSerializer

/**
 * Created by Муса on 12.02.2018.
 */
object ForContractCreateSerializer {

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