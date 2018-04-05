package models.approvaltoapproverlink.daos

import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import org.jooq.generated.tables.ApprovalToApproverLinks.APPROVAL_TO_APPROVER_LINKS
import orm.approvaltoapproverlinkgeneratedrepository.ApprovalToApproverLinkRecord
import models.approvaltoapproverlink.ApprovalToApproverLink
import org.jooq.generated.Tables.APPROVAL_STEP_TO_APPROVER_LINKS

object ApprovalToApproverLinkShowDao {

    val table = APPROVAL_TO_APPROVER_LINKS

    fun whenApprovedByApprovalStepToApproverLinkId(approvalStepToApproverLinkId: Long, userId: Long): ApprovalToApproverLink? {
        return ApprovalToApproverLinkRecord.GET()
                .join {
                    it.approval() {
                        it.approvalSteps() {
                            it.approvalStepToApproverLinks()
                        }
                    }
                }
                .where(
                        APPROVAL_STEP_TO_APPROVER_LINKS.ID.eq(approvalStepToApproverLinkId)
                                .and(table.USER_ID.eq(userId))
                )
                .limit(1)
                .execute()
                .firstOrNull()
    }

}