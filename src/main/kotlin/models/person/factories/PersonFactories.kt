package models.person.factories

import models.person.Person
import utils.requestparameters.IParam

object PersonFactories {

    fun create(params: IParam): Person {
        return Create.create(params)
    }

}