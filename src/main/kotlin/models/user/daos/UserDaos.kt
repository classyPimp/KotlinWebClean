package models.user.daos

import org.jooq.generated.tables.Users
import models.user.daos.UserShowDao
import models.user.daos.UserIndexDao
import models.user.daos.UserEditDao
import models.user.daos.UserUpdateDao
import models.user.daos.UserDestroyDao

object UserDaos {

    val show = UserShowDao

    val index = UserIndexDao

    val edit = UserEditDao

    val update = UserUpdateDao

    val destroy = UserDestroyDao

}