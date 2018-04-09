package models.approvalrejection

import models.approval.Approval
import models.approvalrejectiontouploadeddocumentlink.ApprovalRejectionToUploadedDocumentLink
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import models.discussion.Discussion
import models.user.User
import org.jooq.generated.tables.ApprovalRejections
import orm.annotations.*
import orm.approvalrejectiongeneratedrepository.ApprovalRejectionRecord
import java.sql.Timestamp

@IsModel(jooqTable = ApprovalRejections::class)
class ApprovalRejection {

    val record: ApprovalRejectionRecord by lazy { ApprovalRejectionRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "APPROVAL_ID")
    var approvalId: Long? = null

    @TableField(name = "USER_ID")
    var userId: Long? = null

    @TableField(name = "REASON_TEXT")
    var reasonText: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "FULFILLED")
    var fullfilled: Timestamp? =  null

    @BelongsTo(model = Approval::class, fieldOnThat = "ID", fieldOnThis = "APPROVAL_ID")
    var approval: Approval? = null

    @BelongsTo(model = User::class, fieldOnThat = "ID", fieldOnThis = "USER_ID")
    var user: User? = null

    @HasMany(model = ApprovalRejectionToUploadedDocumentLink::class, fieldOnThis = "ID", fieldOnThat = "APPROVAL_REJECTION_ID")
    var approvalRejectionToUploadedDocumentLinks: MutableList<ApprovalRejectionToUploadedDocumentLink>? = null

    @HasOneAsPolymorphic(model = Discussion::class, fieldOnThat = "DISCUSSABLE_ID", fieldOnThis = "ID", polymorphicTypeField = "DISCUSSABLE_TYPE")
    var discussion: Discussion? = null

}

