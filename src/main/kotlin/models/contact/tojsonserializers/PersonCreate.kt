package models.contact.tojsonserializers

import models.contact.Contact
import orm.contactgeneratedrepository.ContactToJsonSerializer

object PersonCreate {

    fun onSuccess(contact: Contact): String {
        ContactToJsonSerializer(contact).let {
            it.includeContactType()
            return it.serializeToString()
        }
    }

    fun onError(contact: Contact): String {
        ContactToJsonSerializer(contact). let {
            it.includeErrors()
            it.includePersonToContactLink() {
                it.includeErrors()
            }
            return it.serializeToString()
        }
    }

}