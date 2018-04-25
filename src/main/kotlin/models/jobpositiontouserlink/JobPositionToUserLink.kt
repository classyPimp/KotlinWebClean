package models.jobpositiontouserlink

import org.jooq.generated.tables.JobPositionToUserLinks
import orm.annotations.*
import orm.jobpositiontouserlinkgeneratedrepository.JobPositionToUserLinkRecord
import java.sql.Timestamp
import org.jooq.generated.tables.Users
import models.user.User
import org.jooq.generated.tables.JobPositionDelegations
import models.jobpositiondelegation.JobPositionDelegation

@IsModel(jooqTable = JobPositionToUserLinks::class)
class JobPositionToUserLink {

    val record: JobPositionToUserLinkRecord by lazy { JobPositionToUserLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "USER_ID")
    var userId: Long? = null

    @TableField(name = "IS_DELEGATED")
    var isDelegated: Boolean? = null

    @TableField(name = "JOB_POSITION_DELEGATION_ID")
    var jobPositionDelegationId: Long? = null

    @BelongsTo(model = User::class, fieldOnThis = "USER_ID", fieldOnThat = "ID")
    var user: User? = null

    @BelongsTo(model = JobPositionDelegation::class, fieldOnThis = "JOB_POSITION_DELEGATION_ID", fieldOnThat = "ID")
    var jobPositionDelegation: JobPositionDelegation? = null


}

