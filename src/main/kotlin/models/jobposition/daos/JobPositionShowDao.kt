package models.jobposition.daos

import models.jobposition.JobPosition
import orm.jobpositiongeneratedrepository.JobPositionRecord
import org.jooq.generated.Tables.JOB_POSITIONS

object JobPositionShowDao {

    val table = JOB_POSITIONS

    fun isExistsWithId(id: Long): Boolean {
        return JobPositionRecord.GET()
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull() != null
    }

    fun isExistsWithSuchName(name: String): Boolean {
        return JobPositionRecord.GET()
                .where(table.NAME.eq(name))
                .limit(1)
                .execute()
                .firstOrNull() != null
    }

    fun byId(id: Long): JobPosition? {
        return JobPositionRecord.GET()
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull()
    }


}