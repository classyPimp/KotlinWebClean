package models.approvalsteptoapproverlink

import models.approvalrejection.ApprovalRejection
import models.approvalstep.ApprovalStep
import models.user.User
import org.apache.commons.lang3.mutable.Mutable
import org.jooq.generated.tables.ApprovalStepToApproverLinks
import orm.annotations.*
import orm.approvalsteptoapproverlinkgeneratedrepository.ApprovalStepToApproverLinkRecord
import java.sql.Timestamp
@IsModel(jooqTable = ApprovalStepToApproverLinks::class)
class ApprovalStepToApproverLink {

    val record: ApprovalStepToApproverLinkRecord by lazy { ApprovalStepToApproverLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "APPROVAL_STEP_ID")
    var approvalStepId: Long? = null

    @TableField(name = "USER_ID")
    var userId: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "IS_APPROVED")
    var isApproved: Timestamp? = null

    @BelongsTo(model = ApprovalStep::class, fieldOnThis = "APPROVAL_STEP_ID", fieldOnThat = "ID")
    var approvalStep: ApprovalStep? = null

    @BelongsTo(model = User::class, fieldOnThat = "ID", fieldOnThis = "USER_ID")
    var user: User? = null

    @HasMany(model = ApprovalRejection::class, fieldOnThis = "ID", fieldOnThat = "APPROVAL_STEP_TO_APPROVER_LINK_ID")
    var approvalRejections: MutableList<ApprovalRejection>? = null

}


