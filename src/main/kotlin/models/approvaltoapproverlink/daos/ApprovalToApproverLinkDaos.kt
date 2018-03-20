package models.approvaltoapproverlink.daos

import org.jooq.generated.tables.ApprovalToApproverLinks
import models.approvaltoapproverlink.daos.ApprovalToApproverLinkShowDao
import models.approvaltoapproverlink.daos.ApprovalToApproverLinkIndexDao
import models.approvaltoapproverlink.daos.ApprovalToApproverLinkEditDao
import models.approvaltoapproverlink.daos.ApprovalToApproverLinkUpdateDao
import models.approvaltoapproverlink.daos.ApprovalToApproverLinkDestroyDao

object ApprovalToApproverLinkDaos {

    val show = ApprovalToApproverLinkShowDao

    val index = ApprovalToApproverLinkIndexDao

    val edit = ApprovalToApproverLinkEditDao

    val update = ApprovalToApproverLinkUpdateDao

    val destroy = ApprovalToApproverLinkDestroyDao

}