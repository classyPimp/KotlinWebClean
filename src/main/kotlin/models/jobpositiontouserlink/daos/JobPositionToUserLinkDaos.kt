package models.jobpositiontouserlink.daos

import org.jooq.generated.tables.JobPositionToUserLinks
import models.jobpositiontouserlink.daos.JobPositionToUserLinkShowDao
import models.jobpositiontouserlink.daos.JobPositionToUserLinkIndexDao
import models.jobpositiontouserlink.daos.JobPositionToUserLinkEditDao
import models.jobpositiontouserlink.daos.JobPositionToUserLinkUpdateDao
import models.jobpositiontouserlink.daos.JobPositionToUserLinkDestroyDao

object JobPositionToUserLinkDaos {

    val show = JobPositionToUserLinkShowDao

    val index = JobPositionToUserLinkIndexDao

    val edit = JobPositionToUserLinkEditDao

    val update = JobPositionToUserLinkUpdateDao

    val destroy = JobPositionToUserLinkDestroyDao

}