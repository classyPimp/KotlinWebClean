package models.persontocounterpartylink.tojsonserializers

import models.persontocounterpartylink.PersonToCounterPartyLink
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkToJsonSerializer

object Create {

    fun onSuccess(personToCounterPartyLink: PersonToCounterPartyLink): String {
        PersonToCounterPartyLinkToJsonSerializer(personToCounterPartyLink).let {
            it.includePerson()
            it.includePersonToCounterPartyLinkReason()
            return it.serializeToString()
        }
    }

    fun onError(personToCounterPartyLink: PersonToCounterPartyLink): String {
        PersonToCounterPartyLinkToJsonSerializer(personToCounterPartyLink). let {
            it.includePerson() {
                it.includeErrors()
            }
            it.includePersonToCounterPartyLinkReason() {
                it.includeErrors()
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}