package models.discussion

import models.approvalrejection.ApprovalRejection
import models.contract.Contract
import models.discussionmessage.DiscussionMessage
import models.user.User
import org.jooq.generated.tables.Discussions
import orm.annotations.*
import orm.discussiongeneratedrepository.DiscussionRecord
import java.sql.Timestamp

@IsModel(jooqTable = Discussions::class)
class Discussion {

    val record: DiscussionRecord by lazy { DiscussionRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "TOPIC")
    var topic: String? = null

    @TableField(name = "USER_ID")
    var userId: Long? = null

    @TableField(name = "DISCUSSABLE_ID")
    var discussableId: Long? = null

    @TableField(name = "DISCUSSABLE_TYPE")
    var discussableType: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    var discussable: Any? = null

    @BelongsTo(model = User::class, fieldOnThis = "USER_ID", fieldOnThat = "ID")
    var user: User? = null

    @HasMany(model = DiscussionMessage::class, fieldOnThat = "DISCUSSION_ID", fieldOnThis = "ID")
    var discussionMessages: MutableList<DiscussionMessage>? = null

    @BelongsTo(model = ApprovalRejection::class, fieldOnThis = "DISCUSSABLE_ID", fieldOnThat = "ID")
    var approvalRejection: ApprovalRejection? = null

}

