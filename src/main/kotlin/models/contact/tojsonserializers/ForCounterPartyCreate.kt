package models.contact.tojsonserializers

import models.contact.Contact
import orm.contactgeneratedrepository.ContactToJsonSerializer

object ForCounterPartyCreate {

    fun onSuccess(contact: Contact): String {
        ContactToJsonSerializer(contact).let {
            it.includeContactType()
            it.includeCounterPartyToContactLink()
            return it.serializeToString()
        }
    }

    fun onError(contact: Contact): String {
        ContactToJsonSerializer(contact). let {
            it.includeContactType() {
                it.includeErrors()
            }
            it.includeCounterPartyToContactLink() {
                it.includeErrors()
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}