package models.contacttype.factories

import models.contacttype.ContactType
import utils.requestparameters.IParam
import utils.stdlibextensions.trimAndSquishWhiteSpace
import java.util.regex.Pattern

object Create {

    fun create(params: IParam): ContactType {
        return ContactType().also {
            it.name = params.get("name")?.string?.trimAndSquishWhiteSpace()
        }
    }

}