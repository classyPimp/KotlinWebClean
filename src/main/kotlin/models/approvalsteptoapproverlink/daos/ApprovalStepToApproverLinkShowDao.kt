package models.approvalsteptoapproverlink.daos

import org.jooq.generated.tables.ApprovalStepToApproverLinks.APPROVAL_STEP_TO_APPROVER_LINKS
import orm.approvalsteptoapproverlinkgeneratedrepository.ApprovalStepToApproverLinkRecord
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import org.jooq.generated.Tables.APPROVALS
import org.jooq.generated.Tables.APPROVAL_STEPS

object ApprovalStepToApproverLinkShowDao {

    val table = APPROVAL_STEP_TO_APPROVER_LINKS

    fun find(id: Long): ApprovalStepToApproverLink? {
        return ApprovalStepToApproverLinkRecord.GET()
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun existsForUserAndApproval(userId: Long, approvalId: Long): ApprovalStepToApproverLink? {
        return ApprovalStepToApproverLinkRecord.GET()
                .join {
                    it.approvalStep() {
                        it.approval() {
                            it.selectQuery.addJoin(APPROVALS, APPROVALS.LAST_STAGE_ID.eq(APPROVAL_STEPS.ID))
                        }
                    }
                }
                .where(
                        table.USER_ID.eq(userId).and(APPROVAL_STEPS.APPROVAL_ID.eq(approvalId))
                )
                .limit(1)
                .execute()
                .firstOrNull()
    }

}