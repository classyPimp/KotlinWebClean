package models.documenttemplatecategory.factories

import models.documenttemplatecategory.DocumentTemplateCategory
import models.documenttemplatecategory.DocumentTemplateCategoryRequestParametersWrapper

object Create {
    fun create(wrappedParams: DocumentTemplateCategoryRequestParametersWrapper): DocumentTemplateCategory {
        return DocumentTemplateCategory().also {
            it.name = wrappedParams.name
            it.description = wrappedParams.description
        }
    }

}