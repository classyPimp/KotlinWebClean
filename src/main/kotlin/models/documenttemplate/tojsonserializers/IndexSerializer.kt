package models.documenttemplate.tojsonserializers

import models.documenttemplate.DocumentTemplate
import orm.documenttemplategeneratedrepository.DocumentTemplateToJsonSerializer

object IndexSerializer {

    fun onSuccess(documentTemplates: MutableList<DocumentTemplate>): String {
        return DocumentTemplateToJsonSerializer.serialize(documentTemplates).toString()
    }


}