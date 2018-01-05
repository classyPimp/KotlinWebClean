package models.contacttype.factories

import models.contacttype.ContactType
import utils.requestparameters.IParam
import utils.stdlibextensions.string.toNullIfBlank
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

object Create {

    fun create(params: IParam): ContactType {
        return ContactType().also {
            it.name = params.get("name")?.string?.trimAndSquishWhiteSpace()
            it.isSpecificForType = params.get("isSpecificForType")?.string?.trimAndSquishWhiteSpace()?.toNullIfBlank()
        }
    }

}