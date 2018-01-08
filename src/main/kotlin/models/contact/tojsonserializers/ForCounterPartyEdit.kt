package models.contact.tojsonserializers

import models.contact.Contact
import orm.contactgeneratedrepository.ContactToJsonSerializer

object ForCounterPartyEdit {

    fun onSuccess(contact: Contact): String {
        return ContactSerializers.ForCounterParties.create.onSuccess(contact)
    }

}