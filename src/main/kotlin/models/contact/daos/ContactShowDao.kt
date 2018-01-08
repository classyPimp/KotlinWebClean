package models.contact.daos

import org.jooq.generated.tables.Contacts
import orm.contactgeneratedrepository.ContactRecord
import models.contact.Contact
import org.jooq.generated.Tables.CONTACTS

object ContactShowDao {

    fun forCounterPartyById(id: Long): Contact? {
        return ContactRecord.GET()
                .where(
                        CONTACTS.ID.eq(id)
                )
                .preload {
                    it.contactType()
                }
                .limit(1)
                .execute()
                .firstOrNull()
    }

}