package models.contractcategory

import models.contractcategory.daos.ContractCategoryDaos
import orm.contractcategorygeneratedrepository.ContractCategoryValidatorTrait

class ContractCategoryValidator(model: ContractCategory) : ContractCategoryValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateName()
        validateDescription()
    }

    fun updateScenario() {
        createScenario()
    }

    fun validateName() {
        val name = model.name
        if (name == null || name.isBlank()) {
            validationManager.addNameError("should not be empty")
            return
        }
        ContractCategoryDaos.show.forCheckingIfWithSuchNameExists(name = name)?.let {
            validationManager.addNameError("such category already exists")
        }
    }

    fun validateDescription() {
        val description = model.description
        if (description == null || description.isBlank()) {
            validationManager.addDescriptionError("should not be empty")
        }
    }

}