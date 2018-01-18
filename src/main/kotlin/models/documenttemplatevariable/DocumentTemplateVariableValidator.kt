package models.documenttemplatevariable

import orm.documenttemplatevariablegeneratedrepository.DocumentTemplateVariableValidatorTrait

class DocumentTemplateVariableValidator(model: DocumentTemplateVariable) : DocumentTemplateVariableValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}