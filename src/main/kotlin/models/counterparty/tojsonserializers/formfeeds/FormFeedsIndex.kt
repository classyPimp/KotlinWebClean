package models.counterparty.tojsonserializers.formfeeds

import models.counterparty.CounterParty
import orm.counterpartygeneratedrepository.CounterPartyToJsonSerializer

/**
 * Created by Муса on 04.01.2018.
 */
object FormFeedsIndex {

    fun onSuccess(counterParties: MutableList<CounterParty>): String {
        return CounterPartyToJsonSerializer.serialize(counterParties) {
            it.includeIncorporationForm()
        }.toString()
    }

}