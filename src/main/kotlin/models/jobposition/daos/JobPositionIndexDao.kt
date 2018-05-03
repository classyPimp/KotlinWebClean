package models.jobposition.daos

import org.jooq.generated.tables.JobPositions
import orm.jobpositiongeneratedrepository.JobPositionRecord
import models.jobposition.JobPosition
import org.jooq.generated.Tables.JOB_POSITIONS

object JobPositionIndexDao {

    val table = JOB_POSITIONS

    fun indexEdit(): MutableList<JobPosition> {
        return JobPositionRecord.GET()
                .execute()
    }


}