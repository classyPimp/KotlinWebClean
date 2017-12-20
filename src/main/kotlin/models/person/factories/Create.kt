package models.person.factories

import models.person.Person
import utils.requestparameters.IParam
import utils.stdlibextensions.trimAndSquishWhiteSpace
import java.util.regex.Pattern


object Create {

    fun create(params: IParam): Person {
        return Person().also {
            it.name = params.get("name")?.string?.trimAndSquishWhiteSpace()
        }
    }

}