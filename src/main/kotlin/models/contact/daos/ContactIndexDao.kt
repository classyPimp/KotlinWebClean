package models.contact.daos

import org.jooq.generated.tables.Contacts
import orm.contactgeneratedrepository.ContactRecord
import models.contact.Contact
import org.jooq.generated.Tables.COUNTER_PARTY_TO_CONTACT_LINKS

object ContactIndexDao {

    fun forCounterPartyByCounterPartyId(counterPartyId: Long): MutableList<Contact> {
        return ContactRecord.GET()
                .preload {
                    it.contactType()
                    it.counterPartyToContactLink()
                }
                .join {
                    it.counterPartyToContactLink()
                }
                .where(
                        COUNTER_PARTY_TO_CONTACT_LINKS.COUNTER_PARTY_ID.eq(counterPartyId)
                )
                .execute()
    }

}