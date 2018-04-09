package models.approval

import models.approvalrejection.ApprovalRejection
import models.approvalstep.ApprovalStep
import models.approvaltoapproverlink.ApprovalToApproverLink
import models.contract.Contract
import org.jooq.generated.tables.Approvals
import orm.annotations.*
import orm.approvalgeneratedrepository.ApprovalRecord
import java.sql.Timestamp

@IsModel(jooqTable = Approvals::class)
class Approval {

    val record: ApprovalRecord by lazy { ApprovalRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "APPROVABLE_ID")
    var approvableId: Long? = null

    @TableField(name = "APPROVABLE_TYPE")
    var approvableType: String? = null

    @TableField(name = "LAST_STAGE_ID")
    var lastStageId: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasMany(model = ApprovalToApproverLink::class, fieldOnThis = "ID", fieldOnThat = "APPROVAL_ID")
    var approvalToApproverLinks: MutableList<ApprovalToApproverLink>? = null

    @HasMany(model = ApprovalStep::class, fieldOnThis = "ID", fieldOnThat = "APPROVAL_ID")
    var approvalSteps: MutableList<ApprovalStep>? = null

    @HasMany(model = ApprovalRejection::class, fieldOnThis = "ID", fieldOnThat = "APPROVAL_ID")
    var approvalRejections: MutableList<ApprovalRejection>? = null

    @BelongsTo(model = Contract::class, fieldOnThat = "ID", fieldOnThis = "APPROVABLE_ID")
    var contract: Contract? = null

}

