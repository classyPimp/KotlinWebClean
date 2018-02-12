package models.contracttocounterpartylink.tojsonserializers.forcontract

import models.contracttocounterpartylink.ContractToCounterPartyLink
import orm.contracttocounterpartylinkgeneratedrepository.ContractToCounterPartyLinkToJsonSerializer

object ForContractUpdateSerializer {

    fun onSuccess(contractToCounterPartyLink: ContractToCounterPartyLink): String {
        ContractToCounterPartyLinkToJsonSerializer(contractToCounterPartyLink).let {

            return it.serializeToString()
        }
    }

    fun onError(contractToCounterPartyLink: ContractToCounterPartyLink): String {
        ContractToCounterPartyLinkToJsonSerializer(contractToCounterPartyLink). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}