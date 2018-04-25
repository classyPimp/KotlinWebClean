package models.jobpositiondelegation

import org.jooq.generated.tables.JobPositionDelegations
import orm.annotations.*
import orm.jobpositiondelegationgeneratedrepository.JobPositionDelegationRecord
import java.sql.Timestamp
import org.jooq.generated.tables.Users
import models.user.User
import org.jooq.generated.tables.JobPositions
import models.jobposition.JobPosition

@IsModel(jooqTable = JobPositionDelegations::class)
class JobPositionDelegation {

    val record: JobPositionDelegationRecord by lazy { JobPositionDelegationRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "DELEGATED_POSITION_ID")
    var delegatedPositionId: Long? = null

    @TableField(name = "DELEGATED_FROM_USER_ID")
    var delegatedFromUserId: Long? = null

    @TableField(name = "DELEGATED_TO_USER_ID")
    var delegatedToUserId: Long? = null

    @TableField(name = "DELEGATED_SINCE")
    var delegatedSince: Timestamp? = null

    @TableField(name = "DELEGATED_TILL")
    var delegatedTill: Timestamp? = null

    @BelongsTo(model = User::class, fieldOnThis = "DELEGATED_FROM_USER_ID", fieldOnThat = "ID")
    var delegatedFromUser: User? = null

    @BelongsTo(model = User::class, fieldOnThis = "DELEGATED_TO_USER_ID", fieldOnThat = "ID")
    var delegatedToUser: User? = null

    @BelongsTo(model = JobPosition::class, fieldOnThis = "DELEGATED_POSITION_ID", fieldOnThat = "ID")
    var delegatedPosition: JobPosition? = null


}

