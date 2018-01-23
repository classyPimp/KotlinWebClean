package models.documenttemplate.tojsonserializers.prevalidations

import models.documenttemplate.DocumentTemplate
import orm.documenttemplategeneratedrepository.DocumentTemplateToJsonSerializer

/**
 * Created by Муса on 23.01.2018.
 */
object PrevalidationCreateSerializer {

    fun onSuccess(documentTemplate: DocumentTemplate): String {
        return DocumentTemplateToJsonSerializer(documentTemplate).let {
            it.includeDocumentTemplateToDocumentTemplateVariableLinks()
            it.serializeToString()
        }
    }

    fun onError(documentTemplate: DocumentTemplate): String {
        return DocumentTemplateToJsonSerializer(documentTemplate).let {
            it.includeDocumentTemplateToDocumentTemplateVariableLinks() {
                it.includeErrors()
            }
            it.includeErrors()
            it.serializeToString()
        }
    }

}