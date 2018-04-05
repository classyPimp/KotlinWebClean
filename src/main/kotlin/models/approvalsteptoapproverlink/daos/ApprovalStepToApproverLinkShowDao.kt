package models.approvalsteptoapproverlink.daos

import org.jooq.generated.tables.ApprovalStepToApproverLinks.APPROVAL_STEP_TO_APPROVER_LINKS
import orm.approvalsteptoapproverlinkgeneratedrepository.ApprovalStepToApproverLinkRecord
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink

object ApprovalStepToApproverLinkShowDao {

    val table = APPROVAL_STEP_TO_APPROVER_LINKS

    fun find(id: Long): ApprovalStepToApproverLink? {
        return ApprovalStepToApproverLinkRecord.GET()
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull()
    }

}