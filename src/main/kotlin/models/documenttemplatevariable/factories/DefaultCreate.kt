package models.documenttemplatevariable.factories

import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.DocumentTemplateVariableRequestParametersWrapper

object DefaultCreate {

    fun create(params: DocumentTemplateVariableRequestParametersWrapper): DocumentTemplateVariable {
        return DocumentTemplateVariable().also {
            it.defaultValue = params.defaultValue
        }
    }

}