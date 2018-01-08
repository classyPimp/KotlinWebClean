package models.contact.updaters

import models.contact.Contact
import models.contact.ContactRequestParametersWrapper


object ContactUpdaters {

    fun personDefault(contact: Contact, params: ContactRequestParametersWrapper) {
        return PersonDefault.update(contact, params)
    }

    fun forCounterPartyDefault(contact: Contact, params: ContactRequestParametersWrapper) {
        return ForCounterPartyDefaultUpdater.update(contact, params)
    }

}