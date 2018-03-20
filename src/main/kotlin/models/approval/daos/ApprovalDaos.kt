package models.approval.daos

import org.jooq.generated.tables.Approvals
import models.approval.daos.ApprovalShowDao
import models.approval.daos.ApprovalIndexDao
import models.approval.daos.ApprovalEditDao
import models.approval.daos.ApprovalUpdateDao
import models.approval.daos.ApprovalDestroyDao

object ApprovalDaos {

    val show = ApprovalShowDao

    val index = ApprovalIndexDao

    val edit = ApprovalEditDao

    val update = ApprovalUpdateDao

    val destroy = ApprovalDestroyDao

}