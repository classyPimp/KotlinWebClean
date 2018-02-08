package models.contracttocounterpartylink.tojsonserializers

import models.contracttocounterpartylink.tojsonserializers.forcontract.ForContractDestroySerializer
import models.contracttocounterpartylink.tojsonserializers.forcontract.ForContractReplaceSerializer

object ContractToCounterPartyLinkSerializers {

    object ForContract {
        val replace = ForContractReplaceSerializer
        val destroy = ForContractDestroySerializer
    }

}