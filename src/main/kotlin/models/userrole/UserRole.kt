package models.userrole

import org.jooq.generated.tables.UserRoles
import orm.annotations.*
import orm.userrolegeneratedrepository.UserRoleRecord
import java.sql.Timestamp
import org.jooq.generated.tables.Users
import models.user.User

@IsModel(jooqTable = UserRoles::class)
class UserRole {

    val record: UserRoleRecord by lazy { UserRoleRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "IS_SPECIFIC")
    var isSpecific: Boolean? = null

    @TableField(name = "SPECIFIC_TO_TYPE")
    var specificToType: String? = null

    @TableField(name = "SPECIFIC_TO_ID")
    var specificToId: Long? = null

    @TableField(name = "NAME")
    var name: String? = null

    @TableField(name = "USER_ID")
    var userId: Long? = null

    @BelongsTo(model = User::class, fieldOnThis = "USER_ID", fieldOnThat = "ID")
    var user: User? = null


}

