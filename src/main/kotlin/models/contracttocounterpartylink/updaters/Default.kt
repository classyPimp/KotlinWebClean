package models.contracttocounterpartylink.updaters

import models.contracttocounterpartylink.ContractToCounterPartyLink


object Default {

    fun update(model: ContractToCounterPartyLink) {

    }

    fun updateWhenContractReplace(model: ContractToCounterPartyLink, counterPartyId: Long) {
        model.record.let {
            it.counterPartyId = counterPartyId
        }
    }

}