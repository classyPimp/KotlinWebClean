package models.persontocounterpartylink.tojsonserializers.forcounterparties

import models.persontocounterpartylink.PersonToCounterPartyLink
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkToJsonSerializer

/**
 * Created by Муса on 03.03.2018.
 */
object ForCounterPartyEdit {

    fun onSuccess(personToCounterPartyLink: PersonToCounterPartyLink): String {
        PersonToCounterPartyLinkToJsonSerializer(personToCounterPartyLink).let {
            it.includePerson()
            it.includePersonToCounterPartyLinkReason()
            return it.serializeToString()
        }
    }

}