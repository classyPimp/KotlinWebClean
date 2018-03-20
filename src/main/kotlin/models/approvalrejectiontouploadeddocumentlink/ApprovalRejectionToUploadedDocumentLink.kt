package models.approvalrejectiontouploadeddocumentlink

import models.approvalrejection.ApprovalRejection
import models.uploadeddocument.UploadedDocument
import org.jooq.generated.tables.ApprovalRejectionToUploadedDocumentLinks
import orm.annotations.*
import orm.approvalrejectiontouploadeddocumentlinkgeneratedrepository.ApprovalRejectionToUploadedDocumentLinkRecord
import java.sql.Timestamp

@IsModel(jooqTable = ApprovalRejectionToUploadedDocumentLinks::class)
class ApprovalRejectionToUploadedDocumentLink {

    val record: ApprovalRejectionToUploadedDocumentLinkRecord by lazy { ApprovalRejectionToUploadedDocumentLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "APPROVAL_REJECTION_ID")
    var approvalRejectionId: Long? = null

    @TableField(name = "UPLOADED_DOCUMENT_ID")
    var uploadedDocumentId: Long? = null

    @TableField(name = "DESCRIPTION")
    var description: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = ApprovalRejection::class, fieldOnThis = "APPROVAL_REJECTION_ID", fieldOnThat = "ID")
    var approvalRejection: ApprovalRejection? = null

    @BelongsTo(model = UploadedDocument::class, fieldOnThat = "ID", fieldOnThis = "UPLOADED_DOCUMENT_ID")
    var uploadedDocument: UploadedDocument? = null

}

