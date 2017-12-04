package models.account

import models.user.User
import org.jooq.generated.tables.Accounts
import org.jooq.generated.tables.Users
import orm.accountgeneratedrepository.AccountRecord
import orm.annotations.*
import java.sql.Timestamp

/**
 * Created by classypimp on 9/20/17.
 */
@IsModel(jooqTable = Accounts::class)
class Account {

    val record: AccountRecord by lazy { AccountRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "USER_ID")
    var userId: Long? = null

    @TableField(name = "PASSWORD")
    var password: String? = null

    @TableField(name = "EMAIL")
    var email: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = User::class, fieldOnThis = "USER_ID", fieldOnThat = "ID")
    var user: User? = null

    var passwordConfirmation: String? = null

}
