package models.jobposition

import models.jobposition.JobPosition
import utils.requestparameters.IParam

import java.sql.Timestamp

import org.jooq.generated.tables.JobPositions
import models.jobposition.JobPositionRequestParametersWrapper

class JobPositionRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val parentJobPositionId: Long? by lazy { requestParameters.get("parentJobPositionId")?.long }
    val name: String? by lazy { requestParameters.get("name")?.string }
    val isDepartment: Boolean? by lazy { requestParameters.get("isDepartment")?.boolean }
    val isUniquePosition: Boolean? by lazy { requestParameters.get("isUniquePosition")?.boolean }
    val uniquenessIdentifier: String? by lazy { requestParameters.get("uniquenessIdentifier")?.string }
    val subordinatePositions: MutableList<JobPositionRequestParametersWrapper>? by lazy {
    requestParameters.get("subordinatePositions")?.paramList()?.let {
        it.mapTo(mutableListOf<JobPositionRequestParametersWrapper>()) {
            JobPositionRequestParametersWrapper(it)
        }
    }
    }
    val parentPosition: JobPositionRequestParametersWrapper? by lazy {
        requestParameters.get("parentPosition")?.let {
            JobPositionRequestParametersWrapper(it)
        }
    }


}