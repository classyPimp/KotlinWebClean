package models.contracttocounterpartylink.tojsonserializers.forcontract

import models.contracttocounterpartylink.ContractToCounterPartyLink
import orm.contracttocounterpartylinkgeneratedrepository.ContractToCounterPartyLinkToJsonSerializer

object ContractToCounterPartyLinkForContractToJsonSerializer {

    fun onSuccess(contractToCounterPartyLinks: MutableList<ContractToCounterPartyLink>): String {
        return ContractToCounterPartyLinkToJsonSerializer.serialize(contractToCounterPartyLinks) {
            it.includeCounterParty() {
                it.includeIncorporationForm()
            }
        }.toString()
    }


}