package models.documenttemplate

import models.documenttemplate.DocumentTemplate
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLinkRequestParametersWrapper
import models.uploadeddocument.UploadedDocumentRequestParametersWrapper
import utils.requestparameters.IParam

class DocumentTemplateRequestParametersWrapper(val requestParameters: IParam) {

    val uploadedDocument = requestParameters.get("uploadedDocument")?.let {
        UploadedDocumentRequestParametersWrapper(it)
    }

    val documentTemplateToDocumentVariableLinks: MutableList<DocumentTemplateToDocumentVariableLinkRequestParametersWrapper>?  = requestParameters.get("documentTemplateToDocumentVariableLinks")?.paramList()?.let {
        parseDocumentTemplateToDocumentVariableLinks(it)
    }

    private fun  parseDocumentTemplateToDocumentVariableLinks(list: List<IParam>): MutableList<DocumentTemplateToDocumentVariableLinkRequestParametersWrapper>? {
        val toReturn = mutableListOf<DocumentTemplateToDocumentVariableLinkRequestParametersWrapper>()
        list.forEach {
            toReturn.add(DocumentTemplateToDocumentVariableLinkRequestParametersWrapper(it))
        }
        if (toReturn.isEmpty()) {
            return null
        } else {
            return toReturn
        }
    }

}