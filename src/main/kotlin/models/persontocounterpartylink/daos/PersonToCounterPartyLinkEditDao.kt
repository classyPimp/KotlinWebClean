package models.persontocounterpartylink.daos

import org.jooq.generated.tables.PersonToCounterPartyLinks
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkRecord
import models.persontocounterpartylink.PersonToCounterPartyLink
import org.jooq.generated.Tables.PERSON_TO_COUNTER_PARTY_LINKS

object PersonToCounterPartyLinkEditDao {

    fun getForUpdateById(id: Long): PersonToCounterPartyLink? {
        return PersonToCounterPartyLinkRecord.GET()
                .where(
                        PERSON_TO_COUNTER_PARTY_LINKS.ID.eq(id)
                )
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun getByIdForEdit(id: Long): PersonToCounterPartyLink? {
        return getForUpdateById(id)
    }

}