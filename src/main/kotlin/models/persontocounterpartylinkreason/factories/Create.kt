package models.persontocounterpartylinkreason.factories

import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReason
import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReasonRequestParametersWrapper
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

object Create {

    fun create(params: PersonToCounterPartyLinkReasonRequestParametersWrapper): PersonToCounterPartyLinkReason {
        return PersonToCounterPartyLinkReason().also {
            it.reasonName = params.reasonName?.trimAndSquishWhiteSpace()
        }
    }

}