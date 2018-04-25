package controllers.jobposition

import composers.jobposition.JobPositionCreateComposer
import controllers.ApplicationControllerBase
import models.jobposition.tojsonserializers.JobPositionCreateToJsonSerializer
import router.src.ServletRequestContext

class JobPositionController(context: ServletRequestContext) : ApplicationControllerBase(context) {

    fun create() {
        val params = requestParams()
        val composer = JobPositionCreateComposer(params)

        composer.onError = {
            renderJson(
                JobPositionCreateToJsonSerializer.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    JobPositionCreateToJsonSerializer.onSuccess(it)
            )
        }

        composer.run()
    }


}