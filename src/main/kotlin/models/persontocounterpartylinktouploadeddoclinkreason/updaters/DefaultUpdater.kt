package models.persontocounterpartylinktouploadeddoclinkreason.updaters

import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper


object DefaultUpdater {

    fun update(model: PersonToCounterPartyLinkToUploadedDocLinkReason, params: PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper) {
        model.record.let {
            it.reasonName = params.reasonName
        }
    }

}