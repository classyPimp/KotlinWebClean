package models.counterparty

import utils.requestparameters.IParam
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

class CounterPartyRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? = requestParameters.get("id")?.string?.toLongOrNull()

    val incorporationFormId: Long? = requestParameters.get("incorporationFormId")?.long()

    val name: String? = requestParameters.get("name")?.string?.trimAndSquishWhiteSpace()

    val nameShort: String? = requestParameters.get("nameShort")?.string?.trimAndSquishWhiteSpace()

}