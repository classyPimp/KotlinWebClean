package models.user.daos

import org.jooq.generated.tables.Users
import orm.usergeneratedrepository.UserRecord
import models.user.User

object UserShowDao {

    val table = Users.USERS

    fun isExists(userId: Long): Boolean {
        val user = UserRecord.GET()
            .where(table.ID.eq(userId))
            .limit(1)
            .execute()
            .firstOrNull()

        return user != null
    }

    fun byId(id: Long): User? {
        return UserRecord.GET()
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull()
    }

}