package models.jobposition

import orm.annotations.*
import orm.jobpositiongeneratedrepository.JobPositionRecord
import java.sql.Timestamp
import org.jooq.generated.tables.JobPositions
import models.jobposition.JobPosition

@IsModel(jooqTable = JobPositions::class)
class JobPosition {

    val record: JobPositionRecord by lazy { JobPositionRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "PARENT_JOB_POSITION_ID")
    var parentJobPositionId: Long? = null

    @TableField(name = "NAME")
    var name: String? = null

    @TableField(name = "IS_DEPARTMENT")
    var isDepartment: Boolean? = null

    @TableField(name = "IS_UNIQUE_POSITION")
    var isUniquePosition: Boolean? = null

    @TableField(name = "UNIQUENESS_IDENTIFIER")
    var uniquenessIdentifier: String? = null

    @HasMany(model = JobPosition::class, fieldOnThis = "ID", fieldOnThat = "PARENT_JOB_POSITION_ID")
    var subordinatePositions: MutableList<JobPosition>? = null

    @BelongsTo(model = JobPosition::class, fieldOnThis = "PARENT_JOB_POSITION_ID", fieldOnThat = "ID")
    var parentPosition: JobPosition? = null


}

