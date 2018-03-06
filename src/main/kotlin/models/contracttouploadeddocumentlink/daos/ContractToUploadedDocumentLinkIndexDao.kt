package models.contracttouploadeddocumentlink.daos

import org.jooq.generated.tables.ContractToUploadedDocumentLinks.CONTRACT_TO_UPLOADED_DOCUMENT_LINKS
import orm.contracttouploadeddocumentlinkgeneratedrepository.ContractToUploadedDocumentLinkRecord
import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink

object ContractToUploadedDocumentLinkIndexDao {

    val table = CONTRACT_TO_UPLOADED_DOCUMENT_LINKS

    fun forIndex(contractId: Long): MutableList<ContractToUploadedDocumentLink> {
        val contractToUploadedDocumentLinks = ContractToUploadedDocumentLinkRecord.GET()
                .preload {
                    it.contractToUploadedDocumentLinkReason()
                    it.uploadedDocument()
                }
                .where(table.CONTRACT_ID.eq(contractId))
                .execute()

        return contractToUploadedDocumentLinks
    }

    fun ofContractIndexEdit(contractId: Long): MutableList<ContractToUploadedDocumentLink> {
        val contractToUploadedDocumentLinks = ContractToUploadedDocumentLinkRecord.GET()
                .preload {
                    it.contractToUploadedDocumentLinkReason()
                    it.uploadedDocument()
                }
                .where(table.CONTRACT_ID.eq(contractId))
                .execute()

        return contractToUploadedDocumentLinks
    }


}