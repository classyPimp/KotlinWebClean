package models.documenttemplate.tojsonserializers

import models.documenttemplate.DocumentTemplate
import orm.documenttemplategeneratedrepository.DocumentTemplateToJsonSerializer

/**
 * Created by Муса on 31.01.2018.
 */
object ArbitraryShowSerializer {

    fun onSuccess(documentTemplate: DocumentTemplate): String {
        DocumentTemplateToJsonSerializer(documentTemplate).let {
            it.includeUploadedDocument()
            it.includeDocumentTemplateToDocumentVariableLinks() {
                it.includeDocumentTemplateVariable()
            }
            it.includeDocumentTemplateCategory()
            return it.serializeToString()
        }
    }

}