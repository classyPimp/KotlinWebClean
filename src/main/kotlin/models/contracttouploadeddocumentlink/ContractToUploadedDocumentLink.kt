package models.contracttouploadeddocumentlink

import models.contract.Contract
import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason
import models.uploadeddocument.UploadedDocument
import org.jooq.generated.tables.ContractToUploadedDocumentLinks
import orm.annotations.*
import orm.contracttouploadeddocumentlinkgeneratedrepository.ContractToUploadedDocumentLinkRecord
import java.sql.Timestamp

@IsModel(jooqTable = ContractToUploadedDocumentLinks::class)
class ContractToUploadedDocumentLink {

    val record: ContractToUploadedDocumentLinkRecord by lazy { ContractToUploadedDocumentLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CONTRACT_ID")
    var contractId: Long? = null

    @TableField(name = "UPLOADED_DOCUMENT_ID")
    var uploadedDocumentId: Long? = null

    @TableField(name = "CONTRACT_TO_UPLOADED_DOCUMENT_LINK_REASON_ID")
    var contractToUploadedDocumentLinkReasonId: Long? = null

    @TableField(name = "DESCRIPTION")
    var description: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = Contract::class, fieldOnThat = "ID", fieldOnThis = "CONTRACT_ID")
    var contract: Contract? = null

    @BelongsTo(model = UploadedDocument::class, fieldOnThat = "ID", fieldOnThis = "UPLOADED_DOCUMENT_ID")
    var uploadedDocument: UploadedDocument? = null

    @BelongsTo(model = ContractToUploadedDocumentLinkReason::class, fieldOnThis = "CONTRACT_TO_UPLOADED_DOCUMENT_LINK_REASON_ID", fieldOnThat = "ID")
    var contractToUploadedDocumentLinkReason: ContractToUploadedDocumentLinkReason? = null

}

