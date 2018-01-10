package models.persontocounterpartylinktouploadeddoclinkreason.tojsonserializers

import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason
import orm.persontocounterpartylinktouploadeddoclinkreasongeneratedrepository.PersonToCounterPartyLinkToUploadedDocLinkReasonToJsonSerializer

object EditSerializer {

    fun onSuccess(personToCounterPartyLinkToUploadedDocLinkReason: PersonToCounterPartyLinkToUploadedDocLinkReason): String {
        PersonToCounterPartyLinkToUploadedDocLinkReasonToJsonSerializer(personToCounterPartyLinkToUploadedDocLinkReason).let {

            return it.serializeToString()
        }
    }

    fun onError(personToCounterPartyLinkToUploadedDocLinkReason: PersonToCounterPartyLinkToUploadedDocLinkReason): String {
        PersonToCounterPartyLinkToUploadedDocLinkReasonToJsonSerializer(personToCounterPartyLinkToUploadedDocLinkReason). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}