package models.approvalstep.daos

import org.jooq.generated.tables.ApprovalSteps.APPROVAL_STEPS
import orm.approvalstepgeneratedrepository.ApprovalStepRecord
import models.approvalstep.ApprovalStep
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import orm.approvalsteptoapproverlinkgeneratedrepository.ApprovalStepToApproverLinkRecord

object ApprovalStepShowDao {

    val table = APPROVAL_STEPS

    fun byId(id: Long): ApprovalStepToApproverLink? {
        return ApprovalStepToApproverLinkRecord.GET()
                .where(
                        table.ID.eq(id)
                )
                .limit(1)
                .execute()
                .firstOrNull()
    }

}