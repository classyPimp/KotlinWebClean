package models.documenttemplatetodocumentvariablelink

import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.DocumentTemplateVariableRequestParametersWrapper
import utils.requestparameters.IParam

class DocumentTemplateToDocumentVariableLinkRequestParametersWrapper(val requestParameters: IParam) {

    val documentTemplateVariableId: Long? = requestParameters.get("documentTemplateVariableId")?.long()

    val id: Long? = requestParameters.get("id")?.long()

    val defaultValue: String? = requestParameters.get("defaultValue")?.string

    val uniqueIdentifier: String? = requestParameters.get("uniqueIdentifier")?.string

    val documentTemplateVariable: DocumentTemplateVariableRequestParametersWrapper? = requestParameters.get("documentTemplateVariable")?.let {
        DocumentTemplateVariableRequestParametersWrapper(it)
    }

}