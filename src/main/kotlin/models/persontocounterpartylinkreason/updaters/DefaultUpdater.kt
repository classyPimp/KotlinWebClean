package models.persontocounterpartylinkreason.updaters

import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReason
import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReasonRequestParametersWrapper
import utils.stdlibextensions.string.trimAndSquishWhiteSpace


object DefaultUpdater {

    fun update(model: PersonToCounterPartyLinkReason, params: PersonToCounterPartyLinkReasonRequestParametersWrapper) {
        model.record.let {
            it.reasonName = params.reasonName
        }
    }

}