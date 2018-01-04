package models.persontocounterpartylink.updaters

import models.persontocounterpartylink.PersonToCounterPartyLink
import models.persontocounterpartylink.PersonToCounterPartyLinkRequestParametersWrapper


object DefaultUpdater {

    fun update(model: PersonToCounterPartyLink, params: PersonToCounterPartyLinkRequestParametersWrapper) {
        model.record.let {
            it.specificDetails = params.specificDetails
            it.personToCounterPartyLinkReasonId = params.personToCounterPartyLinkReasonId
        }
    }

}