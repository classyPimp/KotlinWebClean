package models.approvalsteptoapproverlink.daos

import org.jooq.generated.tables.ApprovalStepToApproverLinks
import models.approvalsteptoapproverlink.daos.ApprovalStepToApproverLinkShowDao
import models.approvalsteptoapproverlink.daos.ApprovalStepToApproverLinkIndexDao
import models.approvalsteptoapproverlink.daos.ApprovalStepToApproverLinkEditDao
import models.approvalsteptoapproverlink.daos.ApprovalStepToApproverLinkUpdateDao
import models.approvalsteptoapproverlink.daos.ApprovalStepToApproverLinkDestroyDao

object ApprovalStepToApproverLinkDaos {

    val show = ApprovalStepToApproverLinkShowDao

    val index = ApprovalStepToApproverLinkIndexDao

    val edit = ApprovalStepToApproverLinkEditDao

    val update = ApprovalStepToApproverLinkUpdateDao

    val destroy = ApprovalStepToApproverLinkDestroyDao

}