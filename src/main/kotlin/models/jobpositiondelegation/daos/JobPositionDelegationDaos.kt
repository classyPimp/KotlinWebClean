package models.jobpositiondelegation.daos

import org.jooq.generated.tables.JobPositionDelegations
import models.jobpositiondelegation.daos.JobPositionDelegationShowDao
import models.jobpositiondelegation.daos.JobPositionDelegationIndexDao
import models.jobpositiondelegation.daos.JobPositionDelegationEditDao
import models.jobpositiondelegation.daos.JobPositionDelegationUpdateDao
import models.jobpositiondelegation.daos.JobPositionDelegationDestroyDao

object JobPositionDelegationDaos {

    val show = JobPositionDelegationShowDao

    val index = JobPositionDelegationIndexDao

    val edit = JobPositionDelegationEditDao

    val update = JobPositionDelegationUpdateDao

    val destroy = JobPositionDelegationDestroyDao

}