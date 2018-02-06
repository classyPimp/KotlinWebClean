package models.contracttocounterpartylink.factories

import models.contracttocounterpartylink.ContractToCounterPartyLink
import models.contracttocounterpartylink.ContractToCounterPartyLinkRequestParametersWrapper

object DefaultFactory {

    fun create(params: ContractToCounterPartyLinkRequestParametersWrapper): ContractToCounterPartyLink {
        return ContractToCounterPartyLink().also {
            it.contractId = params.contractId
            it.counterPartyId = params.counterPartyId
            it.roleAccordingToContract = params.roleAccordingToContract
        }
    }

}