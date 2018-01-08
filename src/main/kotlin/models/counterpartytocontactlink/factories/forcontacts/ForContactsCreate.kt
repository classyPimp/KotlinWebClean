package models.counterpartytocontactlink.factories.forcontacts

import models.counterpartytocontactlink.CounterPartyToContactLink

object ForContactsCreate {

    fun create(counterPartyId: Long): CounterPartyToContactLink {
        return CounterPartyToContactLink().also {
            it.counterPartyId = counterPartyId
        }
    }

}