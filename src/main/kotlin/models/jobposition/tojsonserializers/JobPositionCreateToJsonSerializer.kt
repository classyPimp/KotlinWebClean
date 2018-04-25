package models.jobposition.tojsonserializers

import models.jobposition.JobPosition
import orm.jobpositiongeneratedrepository.JobPositionToJsonSerializer

object JobPositionCreateToJsonSerializer {

    fun onSuccess(jobPosition: JobPosition): String {
        JobPositionToJsonSerializer(jobPosition).let {

            return it.serializeToString()
        }
    }

    fun onError(jobPosition: JobPosition): String {
        JobPositionToJsonSerializer(jobPosition). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}