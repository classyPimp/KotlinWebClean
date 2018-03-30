package models.userrole.daos

import org.jooq.generated.tables.UserRoles
import models.userrole.daos.UserRoleShowDao
import models.userrole.daos.UserRoleIndexDao
import models.userrole.daos.UserRoleEditDao
import models.userrole.daos.UserRoleUpdateDao
import models.userrole.daos.UserRoleDestroyDao

object UserRoleDaos {

    val show = UserRoleShowDao

    val index = UserRoleIndexDao

    val edit = UserRoleEditDao

    val update = UserRoleUpdateDao

    val destroy = UserRoleDestroyDao

}