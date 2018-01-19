package models.documenttemplatevariable.updaters

import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.DocumentTemplateVariableRequestParametersWrapper


object DefaultUpdater {

    fun update(model: DocumentTemplateVariable, params: DocumentTemplateVariableRequestParametersWrapper) {
            model.record.let {
                it.name = params.name
                it.humanizedName = params.humanizedName
                it.description = params.description
                it.defaultValue = params.defaultValue
            }
    }

}