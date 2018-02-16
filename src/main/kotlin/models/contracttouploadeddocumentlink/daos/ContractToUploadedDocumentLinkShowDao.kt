package models.contracttouploadeddocumentlink.daos

import org.jooq.generated.tables.ContractToUploadedDocumentLinks.CONTRACT_TO_UPLOADED_DOCUMENT_LINKS
import orm.contracttouploadeddocumentlinkgeneratedrepository.ContractToUploadedDocumentLinkRecord
import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import org.jooq.generated.Tables.CONTRACTS

object ContractToUploadedDocumentLinkShowDao {

    val table = CONTRACT_TO_UPLOADED_DOCUMENT_LINKS

    fun forDestroy(contractId: Long, id: Long): ContractToUploadedDocumentLink? {
        val contractToUploadedDocumentLink = ContractToUploadedDocumentLinkRecord.GET()
                .preload {
                    it.uploadedDocument()
                }
                .join {
                    it.contract()
                }
                .where(
                        table.ID.eq(id).and(CONTRACTS.ID.eq(contractId))
                )
                .limit(1)
                .execute()
                .firstOrNull()

        return contractToUploadedDocumentLink
    }

    fun forShow(id: Long?): ContractToUploadedDocumentLink? {
        val contractToUploadedDocumentLink = ContractToUploadedDocumentLinkRecord.GET()
                .preload {
                    it.contractToUploadedDocumentLinkReason()
                }
                .where(
                        table.ID.eq(id)
                )
                .limit(1)
                .execute()
                .firstOrNull()

        return contractToUploadedDocumentLink
    }

    fun forEdit(contractId: Long, id: Long): ContractToUploadedDocumentLink? {
        val contractToUploadedDocumentLink = ContractToUploadedDocumentLinkRecord.GET()
                .join {
                    it.contract()
                }
                .where(
                        table.ID.eq(id).and(CONTRACTS.ID.eq(contractId))
                )
                .limit(1)
                .execute()
                .firstOrNull()
        return contractToUploadedDocumentLink
    }


}