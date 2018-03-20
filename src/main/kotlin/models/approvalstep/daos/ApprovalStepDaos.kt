package models.approvalstep.daos

import org.jooq.generated.tables.ApprovalSteps
import models.approvalstep.daos.ApprovalStepShowDao
import models.approvalstep.daos.ApprovalStepIndexDao
import models.approvalstep.daos.ApprovalStepEditDao
import models.approvalstep.daos.ApprovalStepUpdateDao
import models.approvalstep.daos.ApprovalStepDestroyDao

object ApprovalStepDaos {

    val show = ApprovalStepShowDao

    val index = ApprovalStepIndexDao

    val edit = ApprovalStepEditDao

    val update = ApprovalStepUpdateDao

    val destroy = ApprovalStepDestroyDao

}