package models.documenttemplate.factories

import models.documenttemplate.DocumentTemplate
import models.documenttemplate.DocumentTemplateRequestParametersWrapper
import models.documenttemplatecategory.DocumentTemplateCategoryRequestParametersWrapper
import models.documenttemplatecategory.factories.DocumentTemplateCategoryFactories
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import models.documenttemplatetodocumentvariablelink.factories.DocumentTemplateToDocumentVariableLinkFactories
import models.uploadeddocument.factories.UploadedDocumentFactories

object DefaultCreate {

    fun create(params: DocumentTemplateRequestParametersWrapper): DocumentTemplate {
        return DocumentTemplate().also {
            params.documentTemplateToDocumentVariableLinks?.let {
                links ->
                it.documentTemplateToDocumentVariableLinks = DocumentTemplateToDocumentVariableLinkFactories.defaultCreate.createListForDocumentTemplate(links)
            }

            params.uploadedDocument?.let {
                uploadedDocumentParams ->
                it.uploadedDocument = UploadedDocumentFactories.defaultCreate.create(uploadedDocumentParams)
            }

            it.name = params.name

            it.description = params.description

            it.documentTemplateCategoryId = params.documentTemplateCategoryId

            it.documentTemplateCategory = params.documenTemplateCategory?.let {
                DocumentTemplateCategoryFactories.defaultCreate.create(it)
            }

            it.id = params.id
        }
    }

}