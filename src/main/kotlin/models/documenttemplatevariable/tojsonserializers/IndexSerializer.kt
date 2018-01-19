package models.documenttemplatevariable.tojsonserializers

import models.documenttemplatevariable.DocumentTemplateVariable
import orm.documenttemplatevariablegeneratedrepository.DocumentTemplateVariableToJsonSerializer

object IndexSerializer {

    fun onSuccess(documentTemplateVariables: MutableList<DocumentTemplateVariable>): String {
        return DocumentTemplateVariableToJsonSerializer.serialize(documentTemplateVariables).toString()
    }


}