package models.contact.updaters

import models.contact.Contact
import models.contact.ContactRequestParametersWrapper


object ForCounterPartyDefaultUpdater {

    fun update(model: Contact, params: ContactRequestParametersWrapper) {
        model.record.let {
            it.value = params.value
            it.contactTypeId = params.contactTypeId
        }
    }

}