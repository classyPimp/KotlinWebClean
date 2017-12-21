package models.person.factories

import models.person.Person
import utils.requestparameters.IParam
import utils.stdlibextensions.string.trimAndSquishWhiteSpace


object Create {

    fun create(params: IParam): Person {
        return Person().also {
            it.name = params.get("name")?.string?.trimAndSquishWhiteSpace()
        }
    }

}