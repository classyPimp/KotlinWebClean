package models.documenttemplate

import models.documenttemplate.DocumentTemplate
import models.documenttemplatecategory.DocumentTemplateCategory
import models.documenttemplatecategory.DocumentTemplateCategoryRequestParametersWrapper
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLinkRequestParametersWrapper
import models.uploadeddocument.UploadedDocumentRequestParametersWrapper
import utils.requestparameters.IParam
import utils.stdlibextensions.string.trimAndSquishWhiteSpace

class DocumentTemplateRequestParametersWrapper(val requestParameters: IParam) {

    val uploadedDocument = requestParameters.get("uploadedDocument")?.let {
        UploadedDocumentRequestParametersWrapper(it)
    }

    val documentTemplateToDocumentVariableLinks: MutableList<DocumentTemplateToDocumentVariableLinkRequestParametersWrapper>?  = requestParameters.get("documentTemplateToDocumentVariableLinks")?.paramList()?.let {
        parseDocumentTemplateToDocumentVariableLinks(it)
    }

    val id: Long? = requestParameters.get("id")?.long()

    val name: String? = requestParameters.get("name")?.string?.trimAndSquishWhiteSpace()

    val description: String? = requestParameters.get("description")?.string?.trimAndSquishWhiteSpace()

    val documentTemplateCategoryId: Long? = requestParameters.get("documentTemplateCategoryId")?.long()

    val documenTemplateCategory = requestParameters.get("documentTemplateCategory")?.let {
        DocumentTemplateCategoryRequestParametersWrapper(it)
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