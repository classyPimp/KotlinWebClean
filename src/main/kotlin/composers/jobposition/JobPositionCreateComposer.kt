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

    enum class JobPositionToCreateType {
        DEPARTMENT,
        DEPARTMENT_HEAD,
        SUBORDINATE_DEPARTMENT_TO_DEPARTMENT,
        SUBORDINATE_JOB_POSITION_TO_DEPARTMENT
    }

    lateinit var onSuccess: (JobPosition)->Unit
    lateinit var onError: (JobPosition)->Unit

    lateinit var jobPositionToCreate: JobPosition
    lateinit var wrappedParams: JobPositionRequestParametersWrapper
    lateinit var jobPositionToCreateType: JobPositionToCreateType

    override fun beforeCompose(){
        wrapParams()
        setJobPositionToCreateType()
        buildJobPositionToCreate()
        validate()
    }

    private fun wrapParams() {
        params.get("jobPosition")?.let {
            wrappedParams = JobPositionRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun setJobPositionToCreateType() {
        if (wrappedParams.isDepartment == true) {
            if (wrappedParams.parentJobPositionId != null) {
                jobPositionToCreateType = JobPositionToCreateType.SUBORDINATE_DEPARTMENT_TO_DEPARTMENT
            } else {
                jobPositionToCreateType = JobPositionToCreateType.DEPARTMENT
            }
            return
        }

        if (wrappedParams.isDepartmentHead == true) {
            jobPositionToCreateType = JobPositionToCreateType.DEPARTMENT_HEAD
            return
        }

        jobPositionToCreateType = JobPositionToCreateType.SUBORDINATE_JOB_POSITION_TO_DEPARTMENT
    }


    private fun buildJobPositionToCreate() {
        jobPositionToCreate = JobPositionCreateFactory.create(wrappedParams)
    }


    private fun validate() {
        val validator = JobPositionValidator(jobPositionToCreate)
        when (jobPositionToCreateType) {
            JobPositionToCreateType.SUBORDINATE_JOB_POSITION_TO_DEPARTMENT -> {
                validator.validateSubordinateJobPositionToDepartmentScenario()
            }
            JobPositionToCreateType.DEPARTMENT_HEAD -> {
                validator.validateDepartmentHeadScenario()
            }
            JobPositionToCreateType.DEPARTMENT -> {
                validator.validateDepartmentScenario()
            }
            JobPositionToCreateType.SUBORDINATE_DEPARTMENT_TO_DEPARTMENT-> {
                validator.validateSubordinateDeparmentToDeparmentScenario()
            }
            else -> {
                throw IllegalStateException()
            }
        }
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

