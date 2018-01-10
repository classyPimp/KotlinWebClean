package models.persontocounterpartylinktouploadeddoclinkreason.tojsonserializers.formfeeds

import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason
import orm.persontocounterpartylinktouploadeddoclinkreasongeneratedrepository.PersonToCounterPartyLinkToUploadedDocLinkReasonToJsonSerializer

object FormFeedsIndex {

    fun onSuccess(linkReasons: MutableList<PersonToCounterPartyLinkToUploadedDocLinkReason>): String {
        return PersonToCounterPartyLinkToUploadedDocLinkReasonToJsonSerializer.serialize(linkReasons).toString()
    }

}