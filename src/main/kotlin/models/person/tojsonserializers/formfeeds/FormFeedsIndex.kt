package models.person.tojsonserializers.formfeeds

import models.person.Person
import orm.persongeneratedrepository.PersonToJsonSerializer

/**
 * Created by Муса on 04.01.2018.
 */
object FormFeedsIndex {

    fun onSuccess(persons: MutableList<Person>): String {
        return PersonToJsonSerializer.serialize(persons).toString()
    }

}