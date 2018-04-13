package models.approval

import models.approval.Approval
import models.approvalstep.ApprovalStepRequestParametersWrapper
import utils.requestparameters.IParam
import java.sql.Timestamp

class ApprovalRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long() }
    val approvableId: Long? by lazy { requestParameters.get("approvableId")?.long() }
    val approvableType: String? by lazy { requestParameters.get("approvableType")?.string }
    val lastStageId: Long? by lazy { requestParameters.get("lastStageId")?.long() }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val approvalSteps: MutableList<ApprovalStepRequestParametersWrapper>? by lazy {
        requestParameters.get("approvalSteps")?.paramList()?.mapTo(
                mutableListOf<ApprovalStepRequestParametersWrapper>()
        ) {
            ApprovalStepRequestParametersWrapper(it)
        }
    }

}