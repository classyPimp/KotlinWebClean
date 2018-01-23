package models.documenttemplate.tojsonserializers

import models.documenttemplate.DocumentTemplate
import orm.documenttemplategeneratedrepository.DocumentTemplateToJsonSerializer

object ShowSerializer {

    fun onSuccess(documentTemplate: DocumentTemplate): String {
        DocumentTemplateToJsonSerializer(documentTemplate).let {

            return it.serializeToString()
        }
    }

    fun onError(documentTemplate: DocumentTemplate): String {
        DocumentTemplateToJsonSerializer(documentTemplate). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}