package models.persontocounterpartylinktouploadeddocumentlink.tojsonserializers

import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import orm.persontocounterpartylinktouploadeddocumentlinkgeneratedrepository.PersonToCounterPartyLinkToUploadedDocumentLinkToJsonSerializer

object Destroy {

    fun onSuccess(personToCounterPartyLinkToUploadedDocumentLink: PersonToCounterPartyLinkToUploadedDocumentLink): String {
        PersonToCounterPartyLinkToUploadedDocumentLinkToJsonSerializer(personToCounterPartyLinkToUploadedDocumentLink).let {
            it.includeUploadedDocument() {
                it.includeErrors()
            }
            return it.serializeToString()
        }
    }

    fun onError(personToCounterPartyLinkToUploadedDocumentLink: PersonToCounterPartyLinkToUploadedDocumentLink): String {
        PersonToCounterPartyLinkToUploadedDocumentLinkToJsonSerializer(personToCounterPartyLinkToUploadedDocumentLink). let {
            it.includeUploadedDocument() {
                it.includeErrors()
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}