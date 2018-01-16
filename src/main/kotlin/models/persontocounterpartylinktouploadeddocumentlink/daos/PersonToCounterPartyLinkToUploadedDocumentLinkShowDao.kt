package models.persontocounterpartylinktouploadeddocumentlink.daos

import org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocumentLinks.PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOCUMENT_LINKS
import orm.persontocounterpartylinktouploadeddocumentlinkgeneratedrepository.PersonToCounterPartyLinkToUploadedDocumentLinkRecord
import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import org.jooq.generated.Tables.PERSON_TO_COUNTER_PARTY_LINKS

object PersonToCounterPartyLinkToUploadedDocumentLinkShowDao {

    val table = PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOCUMENT_LINKS
    val personToCounterPartyLinkTable = PERSON_TO_COUNTER_PARTY_LINKS

    fun forDestroy(personToCounterPartyLinkId: Long, id: Long): PersonToCounterPartyLinkToUploadedDocumentLink? {
        return PersonToCounterPartyLinkToUploadedDocumentLinkRecord.GET()
                .join {
                    it.personToCounterPartyLink()
                }
                .where(
                        table.ID.eq(id).and(personToCounterPartyLinkTable.ID.eq(personToCounterPartyLinkId))
                )
                .preload {
                    it.uploadedDocument()
                }
                .execute()
                .firstOrNull()
    }


}