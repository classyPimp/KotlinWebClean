package models.avatar

import models.user.User
import org.jooq.generated.tables.Avatars
import orm.annotations.*
import orm.avatargeneratedrepository.AvatarRecord
import java.sql.Timestamp

@IsModel(jooqTable = Avatars::class)
class Avatar {

    val record: AvatarRecord by lazy { AvatarRecord(this) }

    @TableField(name="ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "FILE_NAME")
    var avatarFileName: String? = null

    @TableField(name="USER_ID")
    var userId: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = User::class, fieldOnThis = "USER_ID", fieldOnThat = "ID")
    var user: User? = null

    val file: AvatarFileHandler by lazy { AvatarFileHandler(this) }
        @DelegatesGetter
        get
}