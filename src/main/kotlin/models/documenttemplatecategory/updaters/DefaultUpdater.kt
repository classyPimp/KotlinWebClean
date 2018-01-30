package models.documenttemplatecategory.updaters

import models.documenttemplatecategory.DocumentTemplateCategory
import models.documenttemplatecategory.DocumentTemplateCategoryRequestParametersWrapper


object DefaultUpdater {

    fun update(model: DocumentTemplateCategory, params: DocumentTemplateCategoryRequestParametersWrapper) {
        model.record.let {
            it.name = params.name
            it.description = params.description
        }
    }

}