package models.persontocounterpartylink

import models.persontocounterpartylink.PersonToCounterPartyLink
import utils.requestparameters.IParam
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

class PersonToCounterPartyLinkRequestParametersWrapper(val requestParameters: IParam) {

    val personId: Long? = requestParameters.get("personId")
            ?.long()

    val id: Long? = requestParameters.get("id")
            ?.long()

    val counterPartyId: Long? = requestParameters.get("counterPartyId")
            ?.long()

    val personToCounterPartyLinkReasonId: Long? = requestParameters
            .get("personToCounterPartyLinkReasonId")
            ?.long()

    val specificDetails: String? = requestParameters
            .get("specificDetails")
            ?.string
            ?.trimAndSquishWhiteSpace()

}