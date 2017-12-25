package models.contact.updaters

import models.contact.Contact
import models.contact.ContactRequestParametersWrapper


object ContactUpdaters {

    fun personDefault(contact: Contact, params: ContactRequestParametersWrapper) {
        return PersonDefault.update(contact, params)
    }

}