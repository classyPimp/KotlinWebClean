package models.persontocounterpartylinktouploadeddoclinkreason.tojsonserializers

import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason
import orm.persontocounterpartylinktouploadeddoclinkreasongeneratedrepository.PersonToCounterPartyLinkToUploadedDocLinkReasonToJsonSerializer

object IndexSerializer {

    fun onSuccess(linkReasons: MutableList<PersonToCounterPartyLinkToUploadedDocLinkReason>): String {
        return PersonToCounterPartyLinkToUploadedDocLinkReasonToJsonSerializer.serialize(linkReasons).toString()
    }


}