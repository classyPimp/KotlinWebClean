package models.persontocounterpartylink.tojsonserializers

import models.persontocounterpartylink.PersonToCounterPartyLink
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkToJsonSerializer

object Edit {

    fun onSuccess(personToCounterPartyLink: PersonToCounterPartyLink): String {
        PersonToCounterPartyLinkToJsonSerializer(personToCounterPartyLink).let {
            it.includeCounterParty() {
                it.includeIncorporationForm()
            }
            it.includePerson()
            it.includePersonToCounterPartyLinkReason()
            return it.serializeToString()
        }
    }

    fun onError(personToCounterPartyLink: PersonToCounterPartyLink): String {
        PersonToCounterPartyLinkToJsonSerializer(personToCounterPartyLink). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}