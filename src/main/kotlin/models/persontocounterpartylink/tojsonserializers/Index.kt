package models.persontocounterpartylink.tojsonserializers

import models.persontocounterpartylink.PersonToCounterPartyLink
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkToJsonSerializer

object Index {

    fun onSuccess(personToCounterPartyLinks: MutableList<PersonToCounterPartyLink>): String {
        return PersonToCounterPartyLinkToJsonSerializer.serialize(personToCounterPartyLinks).toString()
    }


}