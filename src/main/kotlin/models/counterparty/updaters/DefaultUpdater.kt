package models.counterparty.updaters

import models.counterparty.CounterParty
import models.counterparty.CounterPartyRequestParametersWrapper


object DefaultUpdater {

    fun run(model: CounterParty, params: CounterPartyRequestParametersWrapper) {
        model.record.let {
            it.name = params.name
            it.nameShort = params.nameShort
            it.incorporationFormId = params.incorporationFormId
        }
    }

}