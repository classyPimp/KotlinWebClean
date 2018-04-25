package models.jobposition.factories

import models.jobposition.JobPosition
import models.jobposition.JobPositionRequestParametersWrapper

object JobPositionCreateFactory {

    fun create(params: JobPositionRequestParametersWrapper): JobPosition {
        val jobPosition = JobPosition().also {
            it.isDepartment = params.isDepartment
            it.isUniquePosition = params.isUniquePosition
            it.name = params.name
            it.parentJobPositionId
        }

        return jobPosition
    }

}