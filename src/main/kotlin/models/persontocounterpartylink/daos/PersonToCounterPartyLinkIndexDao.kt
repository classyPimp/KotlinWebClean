package models.persontocounterpartylink.daos

import org.jooq.generated.tables.PersonToCounterPartyLinks
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkRecord
import models.persontocounterpartylink.PersonToCounterPartyLink
import org.jooq.generated.Tables.PERSON_TO_COUNTER_PARTY_LINKS

object PersonToCounterPartyLinkIndexDao {
    fun forIndex(): MutableList<PersonToCounterPartyLink> {
        return PersonToCounterPartyLinkRecord.GET().execute()
    }

    fun byCounterPartyId(id: Long): MutableList<PersonToCounterPartyLink> {
        return PersonToCounterPartyLinkRecord.GET()
                .where(
                        PERSON_TO_COUNTER_PARTY_LINKS.COUNTER_PARTY_ID.eq(id)
                )
                .preload {
                    it.person()
                    it.personToCounterPartyLinkReason()
                }
                .execute()
    }


}