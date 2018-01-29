package models.documenttemplate.tojsonserializers

import models.documenttemplate.DocumentTemplate
import orm.documenttemplategeneratedrepository.DocumentTemplateToJsonSerializer

object CreateSerializer {

    fun onSuccess(documentTemplate: DocumentTemplate): String {
        DocumentTemplateToJsonSerializer(documentTemplate).let {
            it.includeDocumentTemplateToDocumentVariableLinks() {
                it.includeDocumentTemplateVariable()
            }
            it.includeUploadedDocument()
            return it.serializeToString()
        }
    }

    fun onError(documentTemplate: DocumentTemplate): String {
        DocumentTemplateToJsonSerializer(documentTemplate). let {
            it.includeDocumentTemplateToDocumentVariableLinks() {
                it.includeErrors()
                it.includeDocumentTemplateVariable() {
                    it.includeErrors()
                }
            }
            it.includeUploadedDocument() {
                it.includeErrors()
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}