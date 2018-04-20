package models.userdefinablerelationreason

import org.jooq.generated.tables.UserDefinableRelationReasons
import orm.annotations.*
import orm.userdefinablerelationreasongeneratedrepository.UserDefinableRelationReasonRecord
import java.sql.Timestamp

@IsModel(jooqTable = UserDefinableRelationReasons::class)
class UserDefinableRelationReason {

    val record: UserDefinableRelationReasonRecord by lazy { UserDefinableRelationReasonRecord(this) }

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

