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