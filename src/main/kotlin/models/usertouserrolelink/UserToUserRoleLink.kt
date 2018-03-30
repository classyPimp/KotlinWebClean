package models.usertouserrolelink

import org.jooq.generated.tables.UserToUserRoleLinks
import orm.annotations.*
import orm.usertouserrolelinkgeneratedrepository.UserToUserRoleLinkRecord
import java.sql.Timestamp
import org.jooq.generated.tables.Users
import models.user.User
import org.jooq.generated.tables.UserRoles
import models.userrole.UserRole

@IsModel(jooqTable = UserToUserRoleLinks::class)
class UserToUserRoleLink {

    val record: UserToUserRoleLinkRecord by lazy { UserToUserRoleLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "USER_ID")
    var userId: Long? = null

    @TableField(name = "USER_ROLE_ID")
    var userRoleId: Long? = null

    @BelongsTo(model = User::class, fieldOnThis = "USER_ID", fieldOnThat = "ID")
    var user: User? = null

    @BelongsTo(model = UserRole::class, fieldOnThis = "USER_ROLE_ID", fieldOnThat = "ID")
    var userRole: UserRole? = null


}

