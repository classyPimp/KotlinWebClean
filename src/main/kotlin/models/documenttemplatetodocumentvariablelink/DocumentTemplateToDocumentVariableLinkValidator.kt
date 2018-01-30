package models.documenttemplatetodocumentvariablelink

import orm.documenttemplatetodocumentvariablelinkgeneratedrepository.DocumentTemplateToDocumentVariableLinkValidatorTrait

class DocumentTemplateToDocumentVariableLinkValidator(model: DocumentTemplateToDocumentVariableLink) : DocumentTemplateToDocumentVariableLinkValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateDocumentTemplateVariable()
    }

    fun whenDocumentTemplateArbitraryCreateScenario() {
        validateDefaultValue()
        validateDocumentTemplateVariable()
    }

    private fun validateDocumentTemplateVariable() {
        val templateVar = model.documentTemplateVariable
        if (templateVar == null) {
            validationManager.addGeneralError("invalid")
            return
        }
        if (!templateVar.record.validationManager.isValid()) {
            validationManager.addGeneralError("has nested errors")
        }
    }

    private fun validateDefaultValue() {
        val defaultValue = model.defaultValue
        if (defaultValue == null || defaultValue.isBlank()) {
            validationManager.addDefaultValueError("should not be empty")
        }
    }

}