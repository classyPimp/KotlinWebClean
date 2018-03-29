package models.approvalsteptoapproverlink

import models.approvalrejection.ApprovalRejectionRequestParametersWrapper
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import models.user.UserRequestParametersWrapper
import utils.requestparameters.IParam

class ApprovalStepToApproverLinkRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long() }
    val approvalStepId: Long? by lazy { requestParameters.get("approvalStepId")?.long() }
    val userId: Long? by lazy { requestParameters.get("userId")?.long() }
    val user: UserRequestParametersWrapper? by lazy {
        requestParameters.get("user")?.let {
            UserRequestParametersWrapper(it)
        }
    }
    val approvalRejection: ApprovalRejectionRequestParametersWrapper? by lazy {
        requestParameters.get("approvalRejection")?.let {
            ApprovalRejectionRequestParametersWrapper(it)
        }
    }


}