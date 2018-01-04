package models.persontocounterpartylink.tojsonserializers

import models.persontocounterpartylink.PersonToCounterPartyLink
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkToJsonSerializer

object Show {

    fun onSuccess(personToCounterPartyLink: PersonToCounterPartyLink): String {
        PersonToCounterPartyLinkToJsonSerializer(personToCounterPartyLink).let {

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