package models.persontocounterpartylink.tojsonserializers

import models.persontocounterpartylink.PersonToCounterPartyLink
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkToJsonSerializer

object PersonToCounterPartyLinkForPersonEditGeneralInfoJsonSerializer {

    fun onSuccess(personToCounterPartyLink: PersonToCounterPartyLink): String {
        PersonToCounterPartyLinkToJsonSerializer(personToCounterPartyLink).let {
            it.includeCounterParty() {
                it.includeIncorporationForm()
            }
            it.includePersonToCounterPartyLinkReason()
            return it.serializeToString()
        }
    }



}