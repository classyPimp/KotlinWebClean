package models.counterparty.tojsonserializers

import models.counterparty.CounterParty
import orm.counterpartygeneratedrepository.CounterPartyToJsonSerializer

object Index {

    fun onSuccess(counterParties: MutableList<CounterParty>): String {
        return CounterPartyToJsonSerializer.serialize(counterParties) {
            it.includeIncorporationForm()
        }.toString()
    }

}