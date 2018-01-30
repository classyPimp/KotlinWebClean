package models.documenttemplatecategory.tojsonserializers

import models.documenttemplatecategory.DocumentTemplateCategory
import orm.documenttemplatecategorygeneratedrepository.DocumentTemplateCategoryToJsonSerializer

object Index {

    fun onSuccess(documentTemplateCategories: MutableList<DocumentTemplateCategory>): String {
        return DocumentTemplateCategoryToJsonSerializer.serialize(documentTemplateCategories).toString()
    }

}