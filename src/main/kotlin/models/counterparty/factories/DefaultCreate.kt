package models.counterparty.factories

import models.counterparty.CounterParty
import models.counterparty.CounterPartyRequestParametersWrapper

object DefaultCreate {

    fun create(params: CounterPartyRequestParametersWrapper): CounterParty {
        return CounterParty().also {
            it.name = params.name
            it.nameShort = params.nameShort
            it.incorporationFormId = params.incorporationFormId
        }
    }

}