package models.documenttemplatevariable

import org.jooq.generated.Tables.DOCUMENT_TEMPLATE_VARIABLES
import orm.documenttemplatevariablegeneratedrepository.DocumentTemplateVariableRecord
import orm.documenttemplatevariablegeneratedrepository.DocumentTemplateVariableValidatorTrait

class DocumentTemplateVariableValidator(model: DocumentTemplateVariable) : DocumentTemplateVariableValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateName()
        validateHumanizedName()
    }

    fun updateScenario() {
        validateName()
        validateHumanizedName()
    }

    private fun validateName() {
        val name = model.name
        if (name == null || name.isNullOrBlank()) {
            validationManager.addNameError("can't be empty")
            return
        }
        if (name.contains(" ")) {
            validationManager.addNameError("can't contain spaces")
            return
        }
        val existingDocumentTemplateVariables = DocumentTemplateVariableRecord.GET()
                .where(
                        DOCUMENT_TEMPLATE_VARIABLES.NAME.eq(name)
                )
                .execute()

        if (existingDocumentTemplateVariables.size > 0) {
            validationManager.addNameError("such name already exists")
        }
    }

    private fun validateHumanizedName() {
        val humanizedName = model.humanizedName
        if (humanizedName == null || humanizedName.isNullOrBlank()) {
            validationManager.addHumanizedNameError("can't be empty")
            return
        }
    }

}