package models.persontocounterpartylinktouploadeddoclinkreason.factories

import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper

object CreateFactory {

    fun create(params: PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper): PersonToCounterPartyLinkToUploadedDocLinkReason {
        return PersonToCounterPartyLinkToUploadedDocLinkReason().also {
            it.reasonName = params.reasonName
        }
    }

}