package models.jobposition

import models.jobposition.daos.JobPositionDaos
import orm.jobpositiongeneratedrepository.JobPositionValidatorTrait

class JobPositionValidator(model: JobPosition) : JobPositionValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateName()
        if (model.record.parentJobPositionId != null) {
            validateParentJobPositionId()
        }
        if (model.record.isUniquePosition == true) {
            validateIsUniquePosition()
        }
    }

    fun validateSubordinateJobPositionToDepartmentScenario() {
        validateName()
        validateThatIsSubordinateToDeparment()
        validateIsUniquePosition()
    }

    fun validateDepartmentHeadScenario() {
        validateName()
        validateThatIsSubordinateToDeparment()
        validateThatIsDeparmentHead()
        validateIsUniquePosition()
    }

    fun validateDepartmentScenario() {
        validateName()
        validateThatIsDepartment()
        validateIsUniquePosition()
    }

    fun validateSubordinateDeparmentToDeparmentScenario() {
        validateDepartmentScenario()
        validateSubordinateJobPositionToDepartmentScenario()
        validateIsUniquePosition()
    }

    private fun validateThatIsDepartment() {
        val isDepartment = model.isDepartment
        if (isDepartment == null) {
            throw IllegalStateException()
        }
        if (isDepartment != true) {
            throw IllegalStateException()
        }
    }

    private fun validateThatIsDeparmentHead() {
        val isDepartmentHead = model.isDepartmentHead
        if (isDepartmentHead != true) {
            throw IllegalStateException()
        }
        validateIsUniquePosition()
    }

    private fun validateThatIsSubordinateToDeparment() {
        val parentJobPositionId = model.parentJobPositionId
        if (parentJobPositionId == null) {
            throw IllegalStateException()
        }
        val parentJobPosition = JobPositionDaos.show.byId(parentJobPositionId)
        if (parentJobPosition == null) {
            validationManager.addParentJobPositionIdError("no such parent position")
            return
        }
        if (parentJobPosition.isDepartment != true) {
            validationManager.addParentJobPositionIdError("parent position is not department")
            return
        }
    }

    private fun validateName() {
        val name = model.name
        if (name == null) {
            validationManager.addNameError("not provided")
            return
        }
    }

    private fun validateParentJobPositionId() {
        val parentJobPositionId = model.parentJobPositionId
        if (parentJobPositionId == null) {
            throw IllegalStateException()
        }
        if (JobPositionDaos.show.isExistsWithId(parentJobPositionId) == false) {
            validationManager.addParentJobPositionIdError("no such parent position")
        }
    }

    private fun validateIsUniquePosition() {
        val name = model.name
        name ?: return
        val isUniquePosition = model.isUniquePosition
        if (isUniquePosition == null) {
            throw IllegalStateException()
        }
        if (JobPositionDaos.show.isExistsWithSuchName(name)) {
            validationManager.addIsUniquePositionError("such position exists, modify it to allow same positions")
        }
    }

    private fun validateUniquenessIdentifier() {
        val uniquenessIdentifier = model.uniquenessIdentifier
        if (uniquenessIdentifier == null) {
            throw IllegalStateException()
        }
    }

}