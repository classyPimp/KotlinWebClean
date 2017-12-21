package models.contact

import models.contact.Contact
import utils.requestparameters.IParam
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

class ContactRequestParametersWrapper(val requestParameters: IParam) {

    var contactTypeId: Long? = requestParameters.get("contactTypeId")?.long()
    var value: String? = requestParameters.get("value")?.string?.trimAndSquishWhiteSpace()

}