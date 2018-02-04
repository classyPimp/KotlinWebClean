package models.contracttouploadeddocumentlinkreason.daos

import org.jooq.generated.tables.ContractToUploadedDocumentLinkReasons
import orm.contracttouploadeddocumentlinkreasongeneratedrepository.ContractToUploadedDocumentLinkReasonRecord
import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason

object ContractToUploadedDocumentLinkReasonIndexDao {
    fun forIndex(): MutableList<ContractToUploadedDocumentLinkReason> {
        return ContractToUploadedDocumentLinkReasonRecord.GET()
                .execute()
    }

    fun forFormFeedsIndex(): MutableList<ContractToUploadedDocumentLinkReason> {
        return forIndex()
    }


}