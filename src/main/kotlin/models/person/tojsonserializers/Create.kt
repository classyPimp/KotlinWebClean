package models.person.tojsonserializers

import models.person.Person
import orm.persongeneratedrepository.PersonToJsonSerializer

object Create {

    fun onSuccess(person: Person): String {
        PersonToJsonSerializer(person).let {
            it.includePersonToContactLinks() {
                it.includeContact() {
                    it.includeContactType()
                }
            }
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