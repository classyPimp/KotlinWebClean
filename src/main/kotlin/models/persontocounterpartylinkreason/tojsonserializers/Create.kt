package models.persontocounterpartylinkreason.tojsonserializers

import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReason
import orm.persontocounterpartylinkreasongeneratedrepository.PersonToCounterPartyLinkReasonToJsonSerializer

object Create {

    fun onSuccess(personToCounterPartyLinkReason: PersonToCounterPartyLinkReason): String {
        PersonToCounterPartyLinkReasonToJsonSerializer(personToCounterPartyLinkReason).let {

            return it.serializeToString()
        }
    }

    fun onError(personToCounterPartyLinkReason: PersonToCounterPartyLinkReason): String {
        PersonToCounterPartyLinkReasonToJsonSerializer(personToCounterPartyLinkReason). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}