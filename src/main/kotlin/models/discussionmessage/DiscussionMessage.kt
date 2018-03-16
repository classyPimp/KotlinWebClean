package models.discussionmessage

import models.discussion.Discussion
import models.user.User
import org.jooq.generated.tables.DiscussionMessages
import orm.annotations.*
import orm.discussionmessagegeneratedrepository.DiscussionMessageRecord
import java.sql.Timestamp

@IsModel(jooqTable = DiscussionMessages::class)
class DiscussionMessage {

    val record: DiscussionMessageRecord by lazy { DiscussionMessageRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "DISCUSSION_ID")
    var discussionId: Long? = null

    @TableField(name = "DISCUSSION_MESSAGE_ID")
    var discussionMessageId: Long? = null

    @TableField(name = "USER_ID")
    var userId: Long? = null

    @TableField(name = "NEST_LEVEL")
    var nestLevel: Int? = null

    @TableField(name = "TEXT")
    var text: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(Discussion::class, fieldOnThis = "DISCUSSION_ID", fieldOnThat = "ID")
    var discussion: Discussion? = null

    @HasMany(DiscussionMessage::class, fieldOnThat = "DISCUSSION_MESSAGE_ID", fieldOnThis = "ID")
    var childMessages: MutableList<DiscussionMessage>? = null

    @BelongsTo(DiscussionMessage::class, fieldOnThis = "DISCUSSION_MESSAGE_ID", fieldOnThat = "ID")
    var parentMessage: DiscussionMessage? = null

    @BelongsTo(User::class, fieldOnThat = "ID", fieldOnThis = "USER_ID")
    var user: User? = null
}

