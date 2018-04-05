package models.user.daos

import models.discussionmessage.DiscussionMessage
import org.jooq.generated.tables.Users
import orm.usergeneratedrepository.UserRecord
import models.user.User
import org.jooq.generated.Tables.USERS
import org.jooq.impl.DSL

object UserIndexDao {

    val table = USERS

    fun byQuery(query: String?): MutableList<User> {
        if (query == null) {
            return UserRecord.GET()
                    .execute()

        }
        return UserRecord.GET()
                .where(
                        DSL.trueCondition()
                                .and("{0} ~* {1}", table.NAME, DSL.`val`(query))
                )
                .execute()
    }

    fun indexAuthorsOfTheseDiscussionMessages(discussionMessages: MutableList<DiscussionMessage>?): MutableList<User> {
        if (discussionMessages == null) {
            return mutableListOf()
        }
        val idList = mutableListOf<Long>()
        discussionMessages.forEach {
            it.userId?.let {
                idList.add(it)
            }
        }
        return UserRecord.GET()
                .where(table.ID.`in`(idList))
                .execute()
    }

}