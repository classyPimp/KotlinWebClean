package models.persontocounterpartylinkreason.daos

import org.jooq.generated.tables.PersonToCounterPartyLinkReasons.PERSON_TO_COUNTER_PARTY_LINK_REASONS
import orm.persontocounterpartylinkreasongeneratedrepository.PersonToCounterPartyLinkReasonRecord
import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReason

object PersonToCounterPartyLinkReasonShowDao {

    fun findById(id: Long): PersonToCounterPartyLinkReason? {
        val link = PersonToCounterPartyLinkReasonRecord.GET().where(
                PERSON_TO_COUNTER_PARTY_LINK_REASONS.ID.eq(id)
        ).limit(1).execute().firstOrNull()

        return link
    }

}