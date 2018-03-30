package models.usertouserrolelink.daos

import org.jooq.generated.tables.UserToUserRoleLinks
import models.usertouserrolelink.daos.UserToUserRoleLinkShowDao
import models.usertouserrolelink.daos.UserToUserRoleLinkIndexDao
import models.usertouserrolelink.daos.UserToUserRoleLinkEditDao
import models.usertouserrolelink.daos.UserToUserRoleLinkUpdateDao
import models.usertouserrolelink.daos.UserToUserRoleLinkDestroyDao

object UserToUserRoleLinkDaos {

    val show = UserToUserRoleLinkShowDao

    val index = UserToUserRoleLinkIndexDao

    val edit = UserToUserRoleLinkEditDao

    val update = UserToUserRoleLinkUpdateDao

    val destroy = UserToUserRoleLinkDestroyDao

}