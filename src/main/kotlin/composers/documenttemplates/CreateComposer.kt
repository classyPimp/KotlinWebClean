package composers.documenttemplates

import models.documenttemplate.DocumentTemplate
import models.documenttemplate.DocumentTemplateRequestParametersWrapper
import models.documenttemplate.factories.DocumentTemplateFactories
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import models.documenttemplatevariable.daos.DocumentTemplateVariableDaos
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class CreateComposer(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (DocumentTemplate)->Unit
    lateinit var onError: (DocumentTemplate)->Unit

    private val state = State()

    override fun beforeCompose(){
        wrapParams()
        buildDocumentTemplateToCreate()
        buildNameToVariableLinks()
        queryDocumentTemplateVariables()
    }

    private fun buildDocumentTemplateToCreate() {
        state.documentTemplate = DocumentTemplateFactories.defaultCreate.create(state.wrappedParams)
    }

    private fun buildNameToVariableLinks() {
        val nameToLinkMap = mutableMapOf<String, MutableList<DocumentTemplateToDocumentVariableLink>>()
        val links = state.documentTemplate.documentTemplateToDocumentVariableLinks
        if (links == null) {
            state.nameToLinkMap = nameToLinkMap
            return
        }
        for (link in links) {
            val name = link.documentTemplateVariable?.name
            if (name == null) {
                state.documentTemplate.record.validationManager.addGeneralError("contains invalid links")
                continue
            }
            val linkList = nameToLinkMap[name]
            if (linkList == null) {
                nameToLinkMap[name] = mutableListOf(link)
            } else {
                linkList.add(link)
            }
        }
        this.state.nameToLinkMap = nameToLinkMap
    }

    private fun queryDocumentTemplateVariables() {
        val names = this.state.nameToLinkMap.keys
        if (names.isEmpty()) {
            return
        }
        val documentTemplateVariables = DocumentTemplateVariableDaos.index.forDocumentTemplatePrevalidationsCreate(names)
        for (it in documentTemplateVariables) {
            val linkList = state.nameToLinkMap[it.name]
            linkList ?: continue

            linkList.forEach {
                link ->
                link.documentTemplateVariable = it
            }
        }

    }

    private fun wrapParams() {
        params.get("documentTemplate")?.let {
            state.wrappedParams = DocumentTemplateRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    override fun compose(){

    }

    override fun fail(error: Throwable) {
        when(error) {

            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(state.documentTemplateCreate)
    }
}

private class State {
    lateinit var documentTemplateCreate: DocumentTemplate
    lateinit var wrappedParams: DocumentTemplateRequestParametersWrapper
    lateinit var documentTemplate: DocumentTemplate
    lateinit var nameToLinkMap: MutableMap<String, MutableList<DocumentTemplateToDocumentVariableLink>>
}
