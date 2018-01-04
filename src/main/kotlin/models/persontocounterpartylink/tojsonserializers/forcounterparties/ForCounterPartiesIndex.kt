package models.persontocounterpartylink.tojsonserializers.forcounterparties

import models.persontocounterpartylink.PersonToCounterPartyLink
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkToJsonSerializer

/**
 * Created by Муса on 04.01.2018.
 */
object ForCounterPartiesIndex {

    fun onSuccess(personToCounterPartyLinks: MutableList<PersonToCounterPartyLink>): String {
        return PersonToCounterPartyLinkToJsonSerializer
                .serialize(personToCounterPartyLinks) {
                    it.includePerson()
                    it.includePersonToCounterPartyLinkReason()
                }
                .toString()
    }

}