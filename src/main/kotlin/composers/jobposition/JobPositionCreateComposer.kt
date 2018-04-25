package composers.jobposition

import models.jobposition.JobPosition
import models.jobposition.JobPositionRequestParametersWrapper
import models.jobposition.JobPositionValidator
import models.jobposition.factories.JobPositionCreateFactory
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class JobPositionCreateComposer(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (JobPosition)->Unit
    lateinit var onError: (JobPosition)->Unit

    lateinit var jobPositionToCreate: JobPosition
    lateinit var wrappedParams: JobPositionRequestParametersWrapper

    override fun beforeCompose(){
        wrapParams()
        buildJobPositionToCreate()
        validate()
    }

    private fun wrapParams() {
        params.get("jobPosition")?.let {
            wrappedParams = JobPositionRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun buildJobPositionToCreate() {
        jobPositionToCreate = JobPositionCreateFactory.create(wrappedParams)
    }


    private fun validate() {
        JobPositionValidator(jobPositionToCreate).createScenario()
        if (!jobPositionToCreate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError("incroporation form invalid"))
        }
    }

    override fun compose(){
        jobPositionToCreate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(jobPositionToCreate)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(jobPositionToCreate)
    }

}

