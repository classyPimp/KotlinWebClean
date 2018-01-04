package models.persontocounterpartylink.factories

import models.persontocounterpartylink.PersonToCounterPartyLink
import models.persontocounterpartylink.PersonToCounterPartyLinkRequestParametersWrapper

object DefaultCreate {

    fun run(params: PersonToCounterPartyLinkRequestParametersWrapper): PersonToCounterPartyLink {
        return PersonToCounterPartyLink().also {
            it.counterPartyId = params.counterPartyId
            it.personId = params.personId
            it.personToCounterPartyLinkReasonId = params.personToCounterPartyLinkReasonId
            it.specificDetails = params.specificDetails
        }
    }

}