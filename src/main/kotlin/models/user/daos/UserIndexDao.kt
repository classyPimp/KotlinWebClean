package models.user.daos

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

}