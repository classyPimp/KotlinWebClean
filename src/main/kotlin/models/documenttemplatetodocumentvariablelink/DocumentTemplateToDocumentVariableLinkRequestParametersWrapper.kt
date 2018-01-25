package models.documenttemplatetodocumentvariablelink

import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.DocumentTemplateVariableRequestParametersWrapper
import utils.requestparameters.IParam

class DocumentTemplateToDocumentVariableLinkRequestParametersWrapper(val requestParameters: IParam) {

    val documentTemplateVariableId: Long? = requestParameters.get("documentTemplateVariable")?.let {
        it.get("documentTemplateVariableId")?.string?.toLongOrNull()
    }

    val defaultValue: String? = requestParameters.get("defaultValue")?.string

    val uniqueIdentifier: String? = requestParameters.get("uniqueIdentifier")?.string

    val documentTemplateVariable: DocumentTemplateVariableRequestParametersWrapper? = requestParameters.get("documentTemplateVariable")?.let {
        DocumentTemplateVariableRequestParametersWrapper(it)
    }
}