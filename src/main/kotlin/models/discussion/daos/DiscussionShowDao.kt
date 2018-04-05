package models.discussion.daos

import org.jooq.generated.tables.Discussions
import orm.discussiongeneratedrepository.DiscussionRecord
import models.discussion.Discussion
import org.jooq.generated.Tables.DISCUSSIONS

object DiscussionShowDao {

    val table = DISCUSSIONS

    fun isExists(id: Long): Boolean {
        return DiscussionRecord.GET()
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull() != null
    }

    fun forShow(id: Long?): Discussion? {
        return DiscussionRecord.GET()
                .preload {
                    it.discussionMessages()
                }
                .limit(1)
                .execute()
                .firstOrNull()
    }

}