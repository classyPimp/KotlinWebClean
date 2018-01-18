package models.documenttemplatevariable.tojsonserializers

import models.documenttemplatevariable.DocumentTemplateVariable
import orm.documenttemplatevariablegeneratedrepository.DocumentTemplateVariableToJsonSerializer

object DestroySerializer {

    fun onSuccess(documentTemplateVariable: DocumentTemplateVariable): String {
        DocumentTemplateVariableToJsonSerializer(documentTemplateVariable).let {

            return it.serializeToString()
        }
    }

    fun onError(documentTemplateVariable: DocumentTemplateVariable): String {
        DocumentTemplateVariableToJsonSerializer(documentTemplateVariable). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}