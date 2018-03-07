package models.person.tojsonserializers

import models.person.Person
import orm.persongeneratedrepository.PersonToJsonSerializer

object PersonShowMinimalJsonSerializer {

    fun onSuccess(person: Person): String {
        PersonToJsonSerializer(person).let {

            return it.serializeToString()
        }
    }

    fun onError(person: Person): String {
        PersonToJsonSerializer(person). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}