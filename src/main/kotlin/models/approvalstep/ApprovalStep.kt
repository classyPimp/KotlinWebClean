package models.approvalstep

import models.approval.Approval
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import models.approvalsteptouploadeddocumentlink.ApprovalStepToUploadedDocumentLink
import org.jooq.generated.tables.ApprovalSteps
import orm.annotations.*
import orm.approvalstepgeneratedrepository.ApprovalStepRecord
import java.sql.Timestamp

@IsModel(jooqTable = ApprovalSteps::class)
class ApprovalStep {

    val record: ApprovalStepRecord by lazy { ApprovalStepRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "APPROVAL_ID")
    var approvalId: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = Approval::class, fieldOnThat = "ID", fieldOnThis = "APPROVAL_ID")
    var approval: Approval? = null

    @HasMany(model = ApprovalStepToApproverLink::class, fieldOnThis = "ID", fieldOnThat = "APPROVAL_STEP_ID")
    var approvalStepToApproverLinks: MutableList<ApprovalStepToApproverLink>? = null

    @HasMany(model = ApprovalStepToUploadedDocumentLink::class, fieldOnThat = "APPROVAL_STEP_ID", fieldOnThis = "ID")
    var approvalStepToUploadedDocumentLinks: MutableList<ApprovalStepToUploadedDocumentLink>? = null

}

