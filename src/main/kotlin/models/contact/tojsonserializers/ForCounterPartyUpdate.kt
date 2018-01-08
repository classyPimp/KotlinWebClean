package models.contact.tojsonserializers

import models.contact.Contact
import orm.contactgeneratedrepository.ContactToJsonSerializer

object ForCounterPartyUpdate {

    fun onSuccess(contact: Contact): String {
        ContactToJsonSerializer(contact).let {

            return it.serializeToString()
        }
    }

    fun onError(contact: Contact): String {
        ContactToJsonSerializer(contact). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}