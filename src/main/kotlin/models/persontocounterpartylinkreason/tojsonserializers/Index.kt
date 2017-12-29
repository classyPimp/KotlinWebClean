package models.persontocounterpartylinkreason.tojsonserializers

import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReason
import orm.persontocounterpartylinkreasongeneratedrepository.PersonToCounterPartyLinkReasonToJsonSerializer

/**
 * Created by Муса on 29.12.2017.
 */
object Index {
    fun onSuccess(personToCounterPartyLinkReasons: MutableList<PersonToCounterPartyLinkReason>): String {
        return PersonToCounterPartyLinkReasonToJsonSerializer
                .serialize(personToCounterPartyLinkReasons)
                .toString()
    }
}