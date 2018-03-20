package models.approvalsteptouploadeddocumentlink

import models.approvalstep.ApprovalStep
import models.uploadeddocument.UploadedDocument
import org.jooq.generated.tables.ApprovalStepToUploadedDocumentLinks
import orm.annotations.*
import orm.approvalsteptouploadeddocumentlinkgeneratedrepository.ApprovalStepToUploadedDocumentLinkRecord
import java.sql.Timestamp

@IsModel(jooqTable = ApprovalStepToUploadedDocumentLinks::class)
class ApprovalStepToUploadedDocumentLink {

    val record: ApprovalStepToUploadedDocumentLinkRecord by lazy { ApprovalStepToUploadedDocumentLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "APPROVAL_STEP_ID")
    var approvalStepId: Long? = null

    @TableField(name = "UPLOADED_DOCUMENT_ID")
    var uploadedDocumentId: Long? = null

    @TableField(name = "DESCRIPTION")
    var description: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = ApprovalStep::class, fieldOnThis = "APPROVAL_STEP_ID", fieldOnThat = "ID")
    var approvalStep: ApprovalStep? = null

    @BelongsTo(model = UploadedDocument::class, fieldOnThat = "ID", fieldOnThis = "UPLOADED_DOCUMENT_ID")
    var uploadedDocument: UploadedDocument? = null

}

