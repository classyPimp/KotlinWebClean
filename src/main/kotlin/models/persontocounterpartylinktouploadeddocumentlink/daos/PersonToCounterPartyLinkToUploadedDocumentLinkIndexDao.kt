package models.persontocounterpartylinktouploadeddocumentlink.daos

import org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocumentLinks
import orm.persontocounterpartylinktouploadeddocumentlinkgeneratedrepository.PersonToCounterPartyLinkToUploadedDocumentLinkRecord
import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink

object PersonToCounterPartyLinkToUploadedDocumentLinkIndexDao {

    fun default(): MutableList<PersonToCounterPartyLinkToUploadedDocumentLink> {
        return PersonToCounterPartyLinkToUploadedDocumentLinkRecord.GET()
                .preload {
                    it.uploadedDocument()
                    it.personToCounterPartyLinkToUploadedDocLinkReason()
                }
                .execute()
    }

}