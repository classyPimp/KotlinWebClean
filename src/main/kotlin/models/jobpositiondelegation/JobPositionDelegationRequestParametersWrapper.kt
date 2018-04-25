package models.jobpositiondelegation

import models.jobpositiondelegation.JobPositionDelegation
import utils.requestparameters.IParam

import java.sql.Timestamp

import org.jooq.generated.tables.Users
import models.user.User
import models.user.UserRequestParametersWrapper
import org.jooq.generated.tables.JobPositions
import models.jobposition.JobPosition
import models.jobposition.JobPositionRequestParametersWrapper

class JobPositionDelegationRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val delegatedPositionId: Long? by lazy { requestParameters.get("delegatedPositionId")?.long }
    val delegatedFromUserId: Long? by lazy { requestParameters.get("delegatedFromUserId")?.long }
    val delegatedToUserId: Long? by lazy { requestParameters.get("delegatedToUserId")?.long }
    val delegatedSince: Timestamp? by lazy { requestParameters.get("delegatedSince")?.timestamp }
    val delegatedTill: Timestamp? by lazy { requestParameters.get("delegatedTill")?.timestamp }
    val delegatedFromUser: UserRequestParametersWrapper? by lazy {
        requestParameters.get("delegatedFromUser")?.let {
            UserRequestParametersWrapper(it)
        }
    }
    val delegatedToUser: UserRequestParametersWrapper? by lazy {
        requestParameters.get("delegatedToUser")?.let {
            UserRequestParametersWrapper(it)
        }
    }
    val delegatedPosition: JobPositionRequestParametersWrapper? by lazy {
        requestParameters.get("delegatedPosition")?.let {
            JobPositionRequestParametersWrapper(it)
        }
    }


}