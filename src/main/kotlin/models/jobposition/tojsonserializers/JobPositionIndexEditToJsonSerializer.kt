package models.jobposition.tojsonserializers

import models.jobposition.JobPosition
import org.apache.commons.lang3.mutable.Mutable
import orm.jobpositiongeneratedrepository.JobPositionToJsonSerializer

/**
 * Created by Муса on 03.05.2018.
 */
object JobPositionIndexEditToJsonSerializer {

    fun onSuccess(models: MutableList<JobPosition>): String {
        return JobPositionToJsonSerializer.serialize(models)
                .toString()
    }

}