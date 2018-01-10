package models.persontocounterpartylinktouploadeddoclinkreason

import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason
import utils.requestparameters.IParam
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

class PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper(val requestParameters: IParam) {

    val reasonName = requestParameters.get("reasonName")?.string?.trimAndSquishWhiteSpace()


}