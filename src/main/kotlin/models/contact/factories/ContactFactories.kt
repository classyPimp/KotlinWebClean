package models.contact.factories

import models.contact.Contact
import models.contact.ContactRequestParametersWrapper

object ContactFactories {

    fun personCreate(params: ContactRequestParametersWrapper, personId: Long): Contact {
        return PersonCreate.create(params, personId)
    }

    fun forCounterPartyCreate(params: ContactRequestParametersWrapper): Contact {
        return ForCounterPartyCreate.create(params)
    }

}