package models.contact.factories

import models.contact.Contact
import models.contact.ContactRequestParametersWrapper

object ForCounterPartyCreate {

    fun  create(params: ContactRequestParametersWrapper, counterPartyId: Long): Contact {
        return Contact().also {
            it.contactTypeId = params.contactTypeId
            it.value = params.value
        }
    }

}