package models.documenttemplatecategory

import models.documenttemplatecategory.daos.DocumentTemplateCategoryDaos
import orm.documenttemplatecategorygeneratedrepository.DocumentTemplateCategoryRecord
import orm.documenttemplatecategorygeneratedrepository.DocumentTemplateCategoryValidatorTrait

class DocumentTemplateCategoryValidator(model: DocumentTemplateCategory) : DocumentTemplateCategoryValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateName()
        validateDescription()
    }

    fun updateScenario() {
        createScenario()
    }

    private fun validateName() {
        val name = model.name
        if (name == null || name.isBlank()) {
            validationManager.addNameError("should not be empty")
            return
        }
        if (name.length > 64) {
            validationManager.addNameError("too long, should be less than 64 characters")
        }
        DocumentTemplateCategoryDaos.show.byNameForUniquenessValidation(name)?.let {
            validationManager.addNameError("such category already exists")
        }
     }

    private fun validateDescription() {
        val description = model.description
        if (description == null || description.isBlank()) {
            validationManager.addDescriptionError("should not be empty")
            return
        }
        if (description.length > 140) {
            validationManager.addDescriptionError("too long should be less than 140 characters")
        }
    }

}