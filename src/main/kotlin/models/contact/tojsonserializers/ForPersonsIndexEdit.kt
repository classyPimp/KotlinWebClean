package models.contact.tojsonserializers

import models.contact.Contact
import orm.contactgeneratedrepository.ContactToJsonSerializer

/**
 * Created by Муса on 01.03.2018.
 */
object ForPersonsIndexEdit {

    fun onSuccess(contacts: MutableList<Contact>): String {
        return ContactToJsonSerializer.serialize(contacts) {
            it.includeContactType()
        }.toString()
    }

}