package models.persontocounterpartylinktouploadeddocumentlink.tojsonserializers

import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import orm.persontocounterpartylinktouploadeddocumentlinkgeneratedrepository.PersonToCounterPartyLinkToUploadedDocumentLinkToJsonSerializer

object Create {

    fun onSuccess(personToCounterPartyLinkToUploadedDocumentLink: PersonToCounterPartyLinkToUploadedDocumentLink): String {
        return PersonToCounterPartyLinkToUploadedDocumentLinkToJsonSerializer(personToCounterPartyLinkToUploadedDocumentLink).also {
            it.includeUploadedDocument()
        }.serializeToString()
    }

    fun onError(personToCounterPartyLinkToUploadedDocumentLink: PersonToCounterPartyLinkToUploadedDocumentLink): String {
        PersonToCounterPartyLinkToUploadedDocumentLinkToJsonSerializer(personToCounterPartyLinkToUploadedDocumentLink). let {
            it.includeErrors()
            it.includeUploadedDocument() {
                it.includeErrors()
            }
            return it.serializeToString()
        }
    }

}