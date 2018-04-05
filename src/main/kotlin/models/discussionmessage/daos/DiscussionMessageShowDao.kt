package models.discussionmessage.daos

import org.jooq.generated.tables.DiscussionMessages
import orm.discussionmessagegeneratedrepository.DiscussionMessageRecord
import models.discussionmessage.DiscussionMessage

object DiscussionMessageShowDao {

    val table = DiscussionMessages.DISCUSSION_MESSAGES

    fun isExists(id: Long): Boolean {
        return DiscussionMessageRecord.GET()
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull() != null
    }


}