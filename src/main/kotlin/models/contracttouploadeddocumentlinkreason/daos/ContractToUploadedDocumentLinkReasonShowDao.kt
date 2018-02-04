package models.contracttouploadeddocumentlinkreason.daos

import org.jooq.generated.tables.ContractToUploadedDocumentLinkReasons.CONTRACT_TO_UPLOADED_DOCUMENT_LINK_REASONS
import orm.contracttouploadeddocumentlinkreasongeneratedrepository.ContractToUploadedDocumentLinkReasonRecord
import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason

object ContractToUploadedDocumentLinkReasonShowDao {

    val table = CONTRACT_TO_UPLOADED_DOCUMENT_LINK_REASONS

    fun forShow(id: Long): ContractToUploadedDocumentLinkReason? {
        return ContractToUploadedDocumentLinkReasonRecord.GET()
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun forUpdate(id: Long): ContractToUploadedDocumentLinkReason? {
        return ContractToUploadedDocumentLinkReasonRecord.GET()
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun forDestroy(id: Long): ContractToUploadedDocumentLinkReason? {
        return forShow(id)
    }

    fun forEdit(id: Long): ContractToUploadedDocumentLinkReason? {
        return forShow(id)
    }


}