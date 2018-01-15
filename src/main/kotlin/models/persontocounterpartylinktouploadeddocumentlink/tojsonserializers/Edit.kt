package models.persontocounterpartylinktouploadeddocumentlink.tojsonserializers

import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import orm.persontocounterpartylinktouploadeddocumentlinkgeneratedrepository.PersonToCounterPartyLinkToUploadedDocumentLinkToJsonSerializer

object Edit {

    fun onSuccess(personToCounterPartyLinkToUploadedDocumentLink: PersonToCounterPartyLinkToUploadedDocumentLink): String {
        PersonToCounterPartyLinkToUploadedDocumentLinkToJsonSerializer(personToCounterPartyLinkToUploadedDocumentLink).let {

            return it.serializeToString()
        }
    }

    fun onError(personToCounterPartyLinkToUploadedDocumentLink: PersonToCounterPartyLinkToUploadedDocumentLink): String {
        PersonToCounterPartyLinkToUploadedDocumentLinkToJsonSerializer(personToCounterPartyLinkToUploadedDocumentLink). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}