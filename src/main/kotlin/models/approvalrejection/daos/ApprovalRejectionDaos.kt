package models.approvalrejection.daos

import org.jooq.generated.tables.ApprovalRejections
import models.approvalrejection.daos.ApprovalRejectionShowDao
import models.approvalrejection.daos.ApprovalRejectionIndexDao
import models.approvalrejection.daos.ApprovalRejectionEditDao
import models.approvalrejection.daos.ApprovalRejectionUpdateDao
import models.approvalrejection.daos.ApprovalRejectionDestroyDao

object ApprovalRejectionDaos {

    val show = ApprovalRejectionShowDao

    val index = ApprovalRejectionIndexDao

    val edit = ApprovalRejectionEditDao

    val update = ApprovalRejectionUpdateDao

    val destroy = ApprovalRejectionDestroyDao

}