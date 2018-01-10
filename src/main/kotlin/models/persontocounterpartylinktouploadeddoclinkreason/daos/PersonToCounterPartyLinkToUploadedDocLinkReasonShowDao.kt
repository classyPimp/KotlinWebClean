package models.persontocounterpartylinktouploadeddoclinkreason.daos

import org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocLinkReasons.PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOC_LINK_REASONS
import orm.persontocounterpartylinktouploadeddoclinkreasongeneratedrepository.PersonToCounterPartyLinkToUploadedDocLinkReasonRecord
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason

object PersonToCounterPartyLinkToUploadedDocLinkReasonShowDao {

    fun byId(id: Long): PersonToCounterPartyLinkToUploadedDocLinkReason? {
        val table = PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOC_LINK_REASONS
        return PersonToCounterPartyLinkToUploadedDocLinkReasonRecord.GET()
                .where(
                        table.ID.eq(id)
                )
                .execute()
                .firstOrNull()
    }


}