package models.user

import models.account.Account
import models.avatar.Avatar
import org.jooq.generated.tables.Users
import orm.annotations.*
import orm.modelUtils.FileItemFileProperty
import orm.usergeneratedrepository.UserRecord
import java.io.File
import java.sql.Timestamp
/**
 * Created by classypimp on 9/13/17.
 */
@IsModel(jooqTable = Users::class)
class User {

    val record: UserRecord by lazy { UserRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "NAME")
    var name: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null


    @HasOne(model = Avatar::class, fieldOnThis = "ID", fieldOnThat = "USER_ID")
    var avatar: Avatar? = null

    @HasOne(model = Account::class, fieldOnThis = "ID", fieldOnThat = "USER_ID")
    var account: Account? = null

}



