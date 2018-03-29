package models.approval

import models.approval.Approval
import models.approvalstep.ApprovalStepRequestParametersWrapper
import models.approvaltoapproverlink.ApprovalToApproverLink
import models.approvaltoapproverlink.ApprovalToApproverLinkRequestParametersWrapper
import utils.requestparameters.IParam
import java.sql.Timestamp

class ApprovalRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long() }
    val approvableId: Long? by lazy { requestParameters.get("approvableId")?.long() }
    val approvableType: String? by lazy { requestParameters.get("approvableType")?.string }
    val lastStageId: Long? by lazy { requestParameters.get("lastStageId")?.long() }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val approvalToApproverLinks: MutableList<ApprovalToApproverLinkRequestParametersWrapper>? by lazy {
        requestParameters.get("approvalToApproverLinks")?.paramList()?.mapTo(
                mutableListOf<ApprovalToApproverLinkRequestParametersWrapper>()
        ) {
            ApprovalToApproverLinkRequestParametersWrapper(it)
        }
    }
    val approvalSteps: MutableList<ApprovalStepRequestParametersWrapper>? by lazy {
        requestParameters.get("approvalSteps")?.paramList()?.mapTo(
                mutableListOf<ApprovalStepRequestParametersWrapper>()
        ) {
            ApprovalStepRequestParametersWrapper(it)
        }
    }

}