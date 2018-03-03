package models.persontocounterpartylink.daos

import org.jooq.generated.tables.PersonToCounterPartyLinks
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkRecord
import models.persontocounterpartylink.PersonToCounterPartyLink
import org.jooq.generated.Tables.PERSON_TO_COUNTER_PARTY_LINKS

object PersonToCounterPartyLinkShowDao {

    val table = PERSON_TO_COUNTER_PARTY_LINKS

    fun getByIdforShow(id: Long): PersonToCounterPartyLink? {
        return PersonToCounterPartyLinkRecord.GET()
                .where(
                        PERSON_TO_COUNTER_PARTY_LINKS.ID.eq(id)
                )
                .execute()
                .firstOrNull()
    }

    fun forCounterPartyEdit(counterPartyId: Long, id: Long): PersonToCounterPartyLink? {
        return PersonToCounterPartyLinkRecord.GET()
                .preload {
                    it.person()
                    it.personToCounterPartyLinkReason()
                }
                .where(
                        PERSON_TO_COUNTER_PARTY_LINKS.ID.eq(id)
                )
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun getByIdforDestroy(id: Long): PersonToCounterPartyLink? {
        return getByIdforShow(id)
    }

    fun forPersonToCounterPartyLinkToUploadedDocumentLinkCreateById(id: Long): PersonToCounterPartyLink? {
        return getByIdforShow(id)
    }

    fun forCounterParty(counterPartyId: Long, id: Long): PersonToCounterPartyLink? {
        val personToCounterPartyLink = PersonToCounterPartyLinkRecord.GET()
                .preload {
                    it.personToCounterPartyLinkReason()
                    it.person()
                }
                .where(table.COUNTER_PARTY_ID.eq(counterPartyId).and(table.ID.eq(id)))
                .limit(1)
                .execute()
                .firstOrNull()
        return personToCounterPartyLink
    }


}