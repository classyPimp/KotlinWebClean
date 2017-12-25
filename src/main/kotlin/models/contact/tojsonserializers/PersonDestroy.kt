package models.contact.tojsonserializers

import models.contact.Contact
import orm.contactgeneratedrepository.ContactToJsonSerializer

object PersonDestroy {

    fun onSuccess(contact: Contact): String {
        ContactToJsonSerializer(contact).let {
            it.includePersonToContactLink()
            return it.serializeToString()
        }
    }

    fun onError(contact: Contact): String {
        ContactToJsonSerializer(contact). let {
            it.includePersonToContactLink() {
                it.includeErrors()
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}