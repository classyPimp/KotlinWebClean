package models.documenttemplate.tojsonserializers

import models.documenttemplate.DocumentTemplate
import orm.documenttemplategeneratedrepository.DocumentTemplateToJsonSerializer

/**
 * Created by Муса on 31.01.2018.
 */
object ArbitraryCreateSerializer {

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
            it.includeDocumentTemplateCategory()
            it.includeErrors()
            return it.serializeToString()
        }
    }

}