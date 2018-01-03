package models.counterparty.tojsonserializers

import models.counterparty.CounterParty
import orm.counterpartygeneratedrepository.CounterPartyToJsonSerializer

object Create {

    fun onSuccess(counterParty: CounterParty): String {
        CounterPartyToJsonSerializer(counterParty).let {
            it.includeIncorporationForm()
            return it.serializeToString()
        }
    }

    fun onError(counterParty: CounterParty): String {
        CounterPartyToJsonSerializer(counterParty). let {
            it.includeIncorporationForm() {
                it.includeErrors()
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}