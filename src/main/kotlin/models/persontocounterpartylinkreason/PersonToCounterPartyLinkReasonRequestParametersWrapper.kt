package models.persontocounterpartylinkreason

import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReason
import utils.requestparameters.IParam
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

class PersonToCounterPartyLinkReasonRequestParametersWrapper(val requestParameters: IParam) {

    val reasonName: String? = requestParameters.get("reasonName")?.string?.trimAndSquishWhiteSpace()

    val id: Long? = requestParameters.get("id")?.long()

}