package models.persontocounterpartylinktouploadeddocumentlink.daos

import org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocumentLinks.PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOCUMENT_LINKS
import orm.persontocounterpartylinktouploadeddocumentlinkgeneratedrepository.PersonToCounterPartyLinkToUploadedDocumentLinkRecord
import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink

object PersonToCounterPartyLinkToUploadedDocumentLinkIndexDao {

    val table = PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOCUMENT_LINKS

    fun default(): MutableList<PersonToCounterPartyLinkToUploadedDocumentLink> {
        return PersonToCounterPartyLinkToUploadedDocumentLinkRecord.GET()
                .preload {
                    it.uploadedDocument()
                    it.personToCounterPartyLinkToUploadedDocLinkReason()
                }
                .execute()
    }

    fun byPersonToCounterPartyLinkId(personToCounterpartyLinkId: Long): MutableList<PersonToCounterPartyLinkToUploadedDocumentLink> {
        return PersonToCounterPartyLinkToUploadedDocumentLinkRecord.GET()
                .where(
                      table.PERSON_TO_COUNTER_PARTY_LINK_ID.eq(personToCounterpartyLinkId)
                )
                .preload {
                    it.uploadedDocument()
                    it.personToCounterPartyLinkToUploadedDocLinkReason()
                }
                .execute()
    }

}