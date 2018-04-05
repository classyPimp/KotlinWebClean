package models.approvaltoapproverlink.daos

import org.jooq.generated.tables.ApprovalToApproverLinks.APPROVAL_TO_APPROVER_LINKS
import orm.approvaltoapproverlinkgeneratedrepository.ApprovalToApproverLinkRecord
import models.approvaltoapproverlink.ApprovalToApproverLink

object ApprovalToApproverLinkIndexDao {

    val table = APPROVAL_TO_APPROVER_LINKS

    fun byApprovalId(approvalId: Long): MutableList<ApprovalToApproverLink>? {
        return ApprovalToApproverLinkRecord.GET()
                .where(
                        table.APPROVAL_ID.eq(approvalId)
                )
                .execute()
    }

}