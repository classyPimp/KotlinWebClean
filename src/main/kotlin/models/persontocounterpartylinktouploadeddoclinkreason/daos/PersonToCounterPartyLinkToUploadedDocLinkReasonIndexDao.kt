package models.persontocounterpartylinktouploadeddoclinkreason.daos

import org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocLinkReasons
import orm.persontocounterpartylinktouploadeddoclinkreasongeneratedrepository.PersonToCounterPartyLinkToUploadedDocLinkReasonRecord
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason

object PersonToCounterPartyLinkToUploadedDocLinkReasonIndexDao {
    fun default(): MutableList<PersonToCounterPartyLinkToUploadedDocLinkReason> {
        return PersonToCounterPartyLinkToUploadedDocLinkReasonRecord.GET()
                .execute()
    }

    fun forFormFeeds(): MutableList<PersonToCounterPartyLinkToUploadedDocLinkReason> {
        return PersonToCounterPartyLinkToUploadedDocLinkReasonRecord.GET()
                .execute()
    }


}