package models.jobpositiontouserlink

import models.jobpositiontouserlink.JobPositionToUserLink
import utils.requestparameters.IParam

import java.sql.Timestamp

import org.jooq.generated.tables.Users
import models.user.User
import models.user.UserRequestParametersWrapper
import org.jooq.generated.tables.JobPositionDelegations
import models.jobpositiondelegation.JobPositionDelegation
import models.jobpositiondelegation.JobPositionDelegationRequestParametersWrapper

class JobPositionToUserLinkRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val userId: Long? by lazy { requestParameters.get("userId")?.long }
    val isDelegated: Boolean? by lazy { requestParameters.get("isDelegated")?.boolean }
    val jobPositionDelegationId: Long? by lazy { requestParameters.get("jobPositionDelegationId")?.long }
    val user: UserRequestParametersWrapper? by lazy {
        requestParameters.get("user")?.let {
            UserRequestParametersWrapper(it)
        }
    }
    val jobPositionDelegation: JobPositionDelegationRequestParametersWrapper? by lazy {
        requestParameters.get("jobPositionDelegation")?.let {
            JobPositionDelegationRequestParametersWrapper(it)
        }
    }


}