package models.contracttouploadeddocumentlinkreason

import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import org.jooq.generated.tables.ContractToUploadedDocumentLinkReasons
import orm.annotations.*
import orm.contracttouploadeddocumentlinkreasongeneratedrepository.ContractToUploadedDocumentLinkReasonRecord
import java.sql.Timestamp

@IsModel(jooqTable = ContractToUploadedDocumentLinkReasons::class)
class ContractToUploadedDocumentLinkReason {

    val record: ContractToUploadedDocumentLinkReasonRecord by lazy { ContractToUploadedDocumentLinkReasonRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "NAME")
    var name: String? = null

    @TableField(name = "DESCRIPTION")
    var description: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasMany(model = ContractToUploadedDocumentLink::class, fieldOnThat = "CONTRACT_TO_UPLOADED_DOCUMENT_LINK_REASON_ID", fieldOnThis = "ID")
    var contractToUploadedDocumentLinks: MutableList<ContractToUploadedDocumentLink>? = null

}

