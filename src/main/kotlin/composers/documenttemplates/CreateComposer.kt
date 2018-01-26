package composers.documenttemplates

import docxtemplating.DocxTemplateVariablesHandler
import models.documenttemplate.DocumentTemplate
import models.documenttemplate.DocumentTemplateRequestParametersWrapper
import models.documenttemplate.DocumentTemplateValidator
import models.documenttemplate.factories.DocumentTemplateFactories
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.daos.DocumentTemplateVariableDaos
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.fileutils.FileNamingUtils
import utils.requestparameters.IParam
import java.io.File

class CreateComposer(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (DocumentTemplate)->Unit
    lateinit var onError: (DocumentTemplate)->Unit

    private val state = State()

    override fun beforeCompose(){
        wrapParams()
        buildDocumentTemplateToCreate()
        buildNameToVariableLinks()
        queryDocumentTemplateVariables()
        try {
            createTempfileForValidation()
        } finally {
            state.tempFile?.delete()
        }
        validate()
    }

    private fun wrapParams() {
        params.get("documentTemplate")?.let {
            state.wrappedParams = DocumentTemplateRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    private fun buildDocumentTemplateToCreate() {
        state.documentTemplate = DocumentTemplateFactories.defaultCreate.create(state.wrappedParams)
    }

    private fun buildNameToVariableLinks() {
        val nameToLinksMap = mutableMapOf<String, MutableList<DocumentTemplateToDocumentVariableLink>>()

        val links = state.documentTemplate.documentTemplateToDocumentVariableLinks

        if (links == null) {
            state.nameToLinksMap = nameToLinksMap
            return
        }

        for (link in links) {
            if (link.documentTemplateVariable == null) {
                link.record.validationManager.addGeneralError("no such variable in database")
                continue
            }

            val name = link.documentTemplateVariable?.name
            if (link.documentTemplateVariable?.id == null) {
                link.record.validationManager.addGeneralError("no such variable in database")
            }
            if (name == null) {
               link.record.validationManager.addGeneralError("variable is invalid: blank provided")
                continue
            }
            val linkList = nameToLinksMap[name]
            if (linkList == null) {
                nameToLinksMap[name] = mutableListOf(link)
            } else {
                linkList.add(link)
            }
        }
        this.state.nameToLinksMap = nameToLinksMap
    }

    private fun queryDocumentTemplateVariables() {
        val names = this.state.nameToLinksMap.keys
        state.variableNamesExtractedFromRequest = names
        if (names.isEmpty()) {
            return
        }
        val documentTemplateVariables = DocumentTemplateVariableDaos.index.forDocumentTemplatePrevalidationsCreate(names)
        val foundNames = mutableListOf<String>()
        for (it in documentTemplateVariables) {
            it.name?.let {
                foundNames.add(it)
            }

            val linkList = state.nameToLinksMap[it.name]
            linkList ?: continue

            linkList.forEach {
                link ->
                link.documentTemplateVariable = it
            }
        }

        val notFoundNames = names.minus(foundNames)
        if (notFoundNames.isNotEmpty()) {
            notFoundNames.forEach {
                name ->
                val links = state.documentTemplate.documentTemplateToDocumentVariableLinks ?: mutableListOf()
                links.add(
                        DocumentTemplateToDocumentVariableLink().also {
                            it.documentTemplateVariable = DocumentTemplateVariable().also {
                                it.name = name
                                it.record.validationManager.addGeneralError("no such link in database")
                            }
                        }
                )
            }
        }
    }

    private fun createTempfileForValidation() {
        try {
            val tempFile = createTempFile(FileNamingUtils.generateUniqueFileName())
            state.tempFile = tempFile
            state.wrappedParams.uploadedDocument?.file?.let {
                it.write(tempFile)
            } ?: throw(Throwable("no file in params"))
        } catch(error: Exception) {
            throw(error)
            state.documentTemplate.record.validationManager.addGeneralError("file invalid")
            failImmediately(ModelInvalidException())
        }
    }

    private fun validateAgainstTemplate() {
        val extractedVariables = DocxTemplateVariablesHandler(state.tempFile!!).extractVariableNamesAsSet()
        var difference: Set<String>
        if (state.variableNamesExtractedFromRequest == null) {
            difference = extractedVariables
        } else {
            difference = extractedVariables.minus(state.variableNamesExtractedFromRequest!!)
        }
        difference.forEach {
            name ->
            val link = state.nameToLinksMap[name]
            if (link == null) {
                if (state.documentTemplate.documentTemplateToDocumentVariableLinks == null) {
                    state.documentTemplate.documentTemplateToDocumentVariableLinks = mutableListOf()
                }
                state.documentTemplate.documentTemplateToDocumentVariableLinks!!.add(
                        DocumentTemplateToDocumentVariableLink().also {
                            it.documentTemplateVariable = DocumentTemplateVariable().also {
                                it.name = name
                                it.record.validationManager.addGeneralError("name ${name}: is not saved to database")
                            }
                        }
                )
            }
        }
    }

    fun validate(){
        DocumentTemplateValidator(state.documentTemplateCreate).createScenario()
        if (!state.documentTemplate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
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
    var variableNamesExtractedFromRequest: MutableSet<String>? = null
    var tempFile: File? = null
    lateinit var documentTemplateCreate: DocumentTemplate
    lateinit var wrappedParams: DocumentTemplateRequestParametersWrapper
    lateinit var documentTemplate: DocumentTemplate
    lateinit var nameToLinksMap: MutableMap<String, MutableList<DocumentTemplateToDocumentVariableLink>>
}
