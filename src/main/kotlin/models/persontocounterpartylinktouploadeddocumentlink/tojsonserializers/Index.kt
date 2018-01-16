package models.persontocounterpartylinktouploadeddocumentlink.tojsonserializers

import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import orm.persontocounterpartylinktouploadeddocumentlinkgeneratedrepository.PersonToCounterPartyLinkToUploadedDocumentLinkToJsonSerializer

object Index {

    fun onSuccess(links: MutableList<PersonToCounterPartyLinkToUploadedDocumentLink>): String {
        return PersonToCounterPartyLinkToUploadedDocumentLinkToJsonSerializer.serialize(links) {
            it.includeUploadedDocument()
            it.includePersonToCounterPartyLinkToUploadedDocLinkReason()
        }.toString()
    }

}