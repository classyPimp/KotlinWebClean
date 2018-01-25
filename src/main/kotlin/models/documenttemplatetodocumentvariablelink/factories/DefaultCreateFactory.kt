package models.documenttemplatetodocumentvariablelink.factories

import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLinkRequestParametersWrapper
import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.factories.DocumentTemplateVariableFactories
import utils.requestparameters.IParam

object DefaultCreateFactory {

    fun createListForDocumentTemplate(paramList: MutableList<DocumentTemplateToDocumentVariableLinkRequestParametersWrapper>): MutableList<DocumentTemplateToDocumentVariableLink> {
        val listToReturn = mutableListOf<DocumentTemplateToDocumentVariableLink>()
        paramList.forEach {
            listToReturn.add(create(it))
        }
        return listToReturn
    }

    fun create(params: DocumentTemplateToDocumentVariableLinkRequestParametersWrapper): DocumentTemplateToDocumentVariableLink {
        return DocumentTemplateToDocumentVariableLink().also {
            it.defaultValue = params.defaultValue
            it.uniqueIdentifier = params.uniqueIdentifier
            it.documentTemplateVariableId = params.documentTemplateVariableId
            it.documentTemplateVariable = params.documentTemplateVariable?.let {
                DocumentTemplateVariableFactories.defaultCreate.create(it)
            }
        }
    }

}