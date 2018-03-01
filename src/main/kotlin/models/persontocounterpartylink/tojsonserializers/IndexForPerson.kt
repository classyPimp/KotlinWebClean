package models.persontocounterpartylink.tojsonserializers

import models.persontocounterpartylink.PersonToCounterPartyLink
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkToJsonSerializer

/**
 * Created by Муса on 01.03.2018.
 */
object IndexForPerson {

    fun onSuccess(personToCounterPartyLinks: MutableList<PersonToCounterPartyLink>): String {
        return PersonToCounterPartyLinkToJsonSerializer.serialize(personToCounterPartyLinks) {
            it.includePersonToCounterPartyLinkReason()
            it.includeCounterParty() {
                it.includeIncorporationForm()
            }
            it.includePersonToCounterPartyLinkToUploadedDocumentLinks() {
                it.includeUploadedDocument()
                it.includePersonToCounterPartyLinkToUploadedDocLinkReason()
            }
        }.toString()
    }

}