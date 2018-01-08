package models.contact.tojsonserializers

import models.contact.Contact
import orm.contactgeneratedrepository.ContactToJsonSerializer

object ForCounterPartyIndex {

    fun onSuccess(contacts: MutableList<Contact>): String {
        return ContactToJsonSerializer.serialize(contacts) {
            it.includeContactType()
        }.toString()
    }


}