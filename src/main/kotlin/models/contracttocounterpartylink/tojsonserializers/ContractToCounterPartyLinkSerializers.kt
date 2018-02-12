package models.contracttocounterpartylink.tojsonserializers

import models.contracttocounterpartylink.tojsonserializers.forcontract.ForContractCreateSerializer
import models.contracttocounterpartylink.tojsonserializers.forcontract.ForContractDestroySerializer
import models.contracttocounterpartylink.tojsonserializers.forcontract.ForContractReplaceSerializer
import models.contracttocounterpartylink.tojsonserializers.forcontract.ForContractUpdateSerializer

object ContractToCounterPartyLinkSerializers {

    object ForContract {
        val replace = ForContractReplaceSerializer
        val destroy = ForContractDestroySerializer
        val create = ForContractCreateSerializer
        val update = ForContractUpdateSerializer
    }

}