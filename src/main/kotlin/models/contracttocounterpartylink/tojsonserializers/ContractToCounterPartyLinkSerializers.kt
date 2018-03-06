package models.contracttocounterpartylink.tojsonserializers

import models.contracttocounterpartylink.tojsonserializers.forcontract.*

object ContractToCounterPartyLinkSerializers {

    object ForContract {
        val replace = ForContractReplaceSerializer
        val destroy = ForContractDestroySerializer
        val create = ForContractCreateSerializer
        val update = ForContractUpdateSerializer
        val index = ContractToCounterPartyLinkForContractToJsonSerializer
    }

}