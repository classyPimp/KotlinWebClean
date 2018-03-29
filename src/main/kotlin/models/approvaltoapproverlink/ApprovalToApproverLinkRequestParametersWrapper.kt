package models.approvaltoapproverlink

import models.approvaltoapproverlink.ApprovalToApproverLink
import models.user.UserRequestParametersWrapper
import utils.requestparameters.IParam
import java.sql.Timestamp

class ApprovalToApproverLinkRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long() }
    val approvalId: Long? by lazy { requestParameters.get("approvalId")?.long() }
    val userId: Long? by lazy { requestParameters.get("userId")?.long() }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val user: UserRequestParametersWrapper? by lazy {
        requestParameters.get("user")?.let {
            UserRequestParametersWrapper(it)
        }
    }

}