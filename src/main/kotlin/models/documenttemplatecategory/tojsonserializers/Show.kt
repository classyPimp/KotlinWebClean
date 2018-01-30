package models.documenttemplatecategory.tojsonserializers

import models.documenttemplatecategory.DocumentTemplateCategory
import orm.documenttemplatecategorygeneratedrepository.DocumentTemplateCategoryToJsonSerializer

object Show {

    fun onSuccess(documentTemplateCategory: DocumentTemplateCategory): String {
        DocumentTemplateCategoryToJsonSerializer(documentTemplateCategory).let {

            return it.serializeToString()
        }
    }

    fun onError(documentTemplateCategory: DocumentTemplateCategory): String {
        DocumentTemplateCategoryToJsonSerializer(documentTemplateCategory). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}