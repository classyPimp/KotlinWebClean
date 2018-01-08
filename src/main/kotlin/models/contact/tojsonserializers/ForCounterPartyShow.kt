package models.contact.tojsonserializers

import models.contact.Contact
import orm.contactgeneratedrepository.ContactToJsonSerializer

object ForCounterPartyShow {

    fun onSuccess(contact: Contact): String {
        return ContactSerializers.ForCounterParties.create.onSuccess(contact)
    }

    fun onError(contact: Contact): String {
        return ContactSerializers.ForCounterParties.create.onError(contact)
    }

}