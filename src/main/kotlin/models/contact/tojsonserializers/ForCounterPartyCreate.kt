package models.contact.tojsonserializers

import models.contact.Contact
import orm.contactgeneratedrepository.ContactToJsonSerializer

object ForCounterPartyCreate {

    fun onSuccess(contact: Contact): String {
        ContactToJsonSerializer(contact).let {
            it.includeContactType()
            return it.serializeToString()
        }
    }

    fun onError(contact: Contact): String {
        ContactToJsonSerializer(contact). let {
            it.includeContactType() {
                it.includeErrors()
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}