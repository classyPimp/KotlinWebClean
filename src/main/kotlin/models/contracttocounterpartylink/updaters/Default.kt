package models.contracttocounterpartylink.updaters

import models.contracttocounterpartylink.ContractToCounterPartyLink
import models.contracttocounterpartylink.ContractToCounterPartyLinkRequestParametersWrapper


object Default {

    fun update(model: ContractToCounterPartyLink, params: ContractToCounterPartyLinkRequestParametersWrapper) {
        model.record.let {
            it.roleAccordingToContract = params.roleAccordingToContract
        }
    }

    fun updateWhenContractReplace(model: ContractToCounterPartyLink, counterPartyId: Long) {
        model.record.let {
            it.counterPartyId = counterPartyId
        }
    }

}