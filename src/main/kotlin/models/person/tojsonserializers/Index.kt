package models.person.tojsonserializers

import models.person.Person
import orm.persongeneratedrepository.PersonToJsonSerializer

object Index {

    fun onSuccess(persons: MutableList<Person>): String {
        return PersonToJsonSerializer.serialize(persons).toString()
    }

}