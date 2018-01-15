package models.persontocounterpartylink.daos

import org.jooq.generated.tables.PersonToCounterPartyLinks
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkRecord
import models.persontocounterpartylink.PersonToCounterPartyLink
import org.jooq.generated.Tables.PERSON_TO_COUNTER_PARTY_LINKS

object PersonToCounterPartyLinkShowDao {
    fun getByIdforShow(id: Long): PersonToCounterPartyLink? {
        return PersonToCounterPartyLinkRecord.GET()
                .where(
                        PERSON_TO_COUNTER_PARTY_LINKS.ID.eq(id)
                )
                .execute()
                .firstOrNull()
    }

    fun getByIdforDestroy(id: Long): PersonToCounterPartyLink? {
        return getByIdforShow(id)
    }

    fun forPersonToCounterPartyLinkToUploadedDocumentLinkCreateById(id: Long): PersonToCounterPartyLink? {
        return getByIdforShow(id)
    }


}