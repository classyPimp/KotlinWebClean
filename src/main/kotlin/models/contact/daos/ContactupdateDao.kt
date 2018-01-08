package models.contact.daos

import org.jooq.generated.tables.Contacts
import orm.contactgeneratedrepository.ContactRecord
import models.contact.Contact
import org.jooq.generated.Tables.CONTACTS
import org.jooq.generated.Tables.COUNTER_PARTY_TO_CONTACT_LINKS

object ContactUpdateDao {

    fun getForCounterParty(counterPartyId: Long, id: Long): Contact? {
        return ContactRecord.GET()
                .join {
                    it.counterPartyToContactLink()
                }
                .where(
                        CONTACTS.ID.eq(id).and(
                                COUNTER_PARTY_TO_CONTACT_LINKS.COUNTER_PARTY_ID.eq(counterPartyId)
                        )
                )
                .limit(1)
                .execute()
                .firstOrNull()
    }

}