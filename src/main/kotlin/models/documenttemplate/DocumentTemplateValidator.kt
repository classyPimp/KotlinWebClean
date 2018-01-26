package models.documenttemplate

import orm.documenttemplategeneratedrepository.DocumentTemplateValidatorTrait

class DocumentTemplateValidator(model: DocumentTemplate) : DocumentTemplateValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){

    }

}