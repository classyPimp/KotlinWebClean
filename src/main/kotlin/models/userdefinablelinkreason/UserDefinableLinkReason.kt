package models.userdefinablelinkreason

import org.jooq.generated.tables.UserDefinableLinkReasons
import orm.annotations.*
import orm.userdefinablelinkreasongeneratedrepository.UserDefinableLinkReasonRecord
import java.sql.Timestamp

@IsModel(jooqTable = UserDefinableLinkReasons::class)
class UserDefinableLinkReason {

    val record: UserDefinableLinkReasonRecord by lazy { UserDefinableLinkReasonRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "REASON_NAME")
    var reasonName: String? = null

    @TableField(name = "FOR_TYPE")
    var forType: String? = null

    @TableField(name = "SUB_IDENTIFIER")
    var subIdentifier: String? = null

    @TableField(name = "REASON_DESCRIPTION")
    var reasonDescription: String? = null


}

