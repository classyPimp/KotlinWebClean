package models.persontocounterpartylinkreason.daos

import org.jooq.generated.tables.PersonToCounterPartyLinkReasons.PERSON_TO_COUNTER_PARTY_LINK_REASONS
import orm.persontocounterpartylinkreasongeneratedrepository.PersonToCounterPartyLinkReasonRecord
import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReason

object PersonToCounterPartyLinkReasonIndexDao {

    fun indexAll(): MutableList<PersonToCounterPartyLinkReason> {
        return PersonToCounterPartyLinkReasonRecord.GET().execute()
    }

}