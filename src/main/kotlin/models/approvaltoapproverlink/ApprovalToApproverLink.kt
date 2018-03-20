package models.approvaltoapproverlink

import models.approval.Approval
import models.user.User
import org.jooq.generated.tables.ApprovalToApproverLinks
import orm.annotations.*
import orm.approvaltoapproverlinkgeneratedrepository.ApprovalToApproverLinkRecord
import java.sql.Timestamp

@IsModel(jooqTable = ApprovalToApproverLinks::class)
class ApprovalToApproverLink {

    val record: ApprovalToApproverLinkRecord by lazy { ApprovalToApproverLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "APPROVAL_ID")
    var approvalId: Long? = null

    @TableField(name = "USER_ID")
    var user_id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = Approval::class, fieldOnThis = "APPROVAL_ID", fieldOnThat = "ID")
    var approval: Approval? = null

    @BelongsTo(model = User::class, fieldOnThat = "ID", fieldOnThis = "USER_ID")
    var user: User? =  null

}

