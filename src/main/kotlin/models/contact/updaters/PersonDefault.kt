package models.contact.updaters

import models.contact.Contact
import models.contact.ContactRequestParametersWrapper


object PersonDefault {

    fun update(model: Contact, params: ContactRequestParametersWrapper) {
        params.let {
            model.record.value = it.value
            model.record.contactTypeId = it.contactTypeId
        }
    }

}