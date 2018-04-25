package models.jobposition.daos

import org.jooq.generated.tables.JobPositions
import models.jobposition.daos.JobPositionShowDao
import models.jobposition.daos.JobPositionIndexDao
import models.jobposition.daos.JobPositionEditDao
import models.jobposition.daos.JobPositionUpdateDao
import models.jobposition.daos.JobPositionDestroyDao

object JobPositionDaos {

    val show = JobPositionShowDao

    val index = JobPositionIndexDao

    val edit = JobPositionEditDao

    val update = JobPositionUpdateDao

    val destroy = JobPositionDestroyDao

}