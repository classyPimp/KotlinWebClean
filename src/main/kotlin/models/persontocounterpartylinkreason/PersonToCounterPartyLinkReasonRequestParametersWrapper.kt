package models.persontocounterpartylinkreason

import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReason
import utils.requestparameters.IParam

class PersonToCounterPartyLinkReasonRequestParametersWrapper(val requestParameters: IParam) {

    val reasonName: String? = requestParameters.get("reasonName")?.string

    val id: Long? = requestParameters.get("id")?.long()

}