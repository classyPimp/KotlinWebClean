package models.documenttemplate

import models.documenttemplatecategory.DocumentTemplateCategory
import models.documenttemplatecategory.daos.DocumentTemplateCategoryDaos
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLinkValidator
import org.jooq.generated.Tables.DOCUMENT_TEMPLATES
import orm.documenttemplategeneratedrepository.DocumentTemplateRecord
import orm.documenttemplategeneratedrepository.DocumentTemplateValidatorTrait

class DocumentTemplateValidator(model: DocumentTemplate) : DocumentTemplateValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateDocumentTemplateToDocumentVariableLinks()
        validateName()
        validateDocumentTemplateCategoryId()
    }

    fun arbitraryCreateScenario() {
        validateDocumentTemplateToDocumentVariableLinksForArbitraryCreate()
    }

    private fun validateDocumentTemplateToDocumentVariableLinks() {
        val links = model.documentTemplateToDocumentVariableLinks
        var hasNestedErrors = false
        links?.forEach {
            DocumentTemplateToDocumentVariableLinkValidator(it).createScenario()
            if (!it.record.validationManager.isValid()) {
                hasNestedErrors = true
            }
        }
        if (hasNestedErrors) {
            validationManager.addGeneralError("has nested errors")
        }
    }

    private fun validateDocumentTemplateToDocumentVariableLinksForArbitraryCreate() {
        val links = model.documentTemplateToDocumentVariableLinks
        var hasNestedErrors = false
        links?.forEach {
            DocumentTemplateToDocumentVariableLinkValidator(it).whenDocumentTemplateArbitraryCreateScenario()
            if (!it.record.validationManager.isValid()) {
                hasNestedErrors = true
            }
        }
        validationManager.addGeneralError("nested errors")
    }

    private fun validateName() {
        val name = model.name
        if (name == null || name.isBlank()) {
            validationManager.addNameError("invalid: should be provided")
            return
        }
        DocumentTemplateRecord.GET().where(DOCUMENT_TEMPLATES.NAME.eq(name)).execute().firstOrNull()?.let {
            validationManager.addNameError("such template name already exists")
        }
    }

    private fun validateDocumentTemplateCategoryId() {
        val id = model.documentTemplateCategoryId
        if (id == null) {
            validationManager.addDocumentTemplateCategoryIdError("should be assigned")
            return
        }
        DocumentTemplateCategoryDaos.show.byIdForShow(id) ?: validationManager.addDocumentTemplateCategoryIdError("no such category exists")
    }

}