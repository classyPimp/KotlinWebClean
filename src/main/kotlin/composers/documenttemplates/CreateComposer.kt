package composers.documenttemplates

import docxtemplating.DocxTemplateVariablesHandler
import models.documenttemplate.DocumentTemplate
import models.documenttemplate.DocumentTemplateRequestParametersWrapper
import models.documenttemplate.DocumentTemplateValidator
import models.documenttemplate.factories.DocumentTemplateFactories
import models.documenttemplate.tojsonserializers.DocumentTemplateSerializers
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.daos.DocumentTemplateVariableDaos
import orm.documenttemplategeneratedrepository.DocumentTemplateToJsonSerializer
import orm.services.ModelInvalidException
import orm.utils.TransactionRunner
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
        createTempfileForValidation()
        validateAgainstTemplate()
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

        val links = state.documentTemplate!!.documentTemplateToDocumentVariableLinks

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
        for (documentTemplateVariable in documentTemplateVariables) {
            documentTemplateVariable.name?.let {
                foundNames.add(it)
            }

            val linkList = state.nameToLinksMap[documentTemplateVariable.name]
            linkList ?: continue

            linkList.forEach {
                link ->
                link.documentTemplateVariableId = documentTemplateVariable.id
                link.documentTemplateVariable = documentTemplateVariable
            }
        }

        val notFoundNames = names.minus(foundNames)

        if (notFoundNames.isNotEmpty()) {
            notFoundNames.forEach {
                name ->
                val nameToLinks = state.nameToLinksMap.get(name)
                nameToLinks?.forEach {
                    it.documentTemplateVariable?.record?.validationManager?.addGeneralError("no such link in database")
                }
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
            state.tempFile?.delete()
            throw(error)
            state.documentTemplate!!.record.validationManager.addGeneralError("file invalid")
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
                if (state.documentTemplate!!.documentTemplateToDocumentVariableLinks == null) {
                    state.documentTemplate!!.documentTemplateToDocumentVariableLinks = mutableListOf()
                }
                state.documentTemplate!!.documentTemplateToDocumentVariableLinks!!.add(
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
        DocumentTemplateValidator(state.documentTemplate!!).createScenario()
        if (!state.documentTemplate!!.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        TransactionRunner.run {
            state.documentTemplate!!.record.saveCascade(
                    dslContext = it.inTransactionDsl,
                    before = {

                    },
                    after = {
                        state.documentTemplate!!.uploadedDocument?.file?.finalizeOperation()
                    }

            )
        }
    }

    override fun fail(error: Throwable) {
        clearResources()
        when(error) {
            is ModelInvalidException -> {
                onError(
                        state.documentTemplate!!
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(state.documentTemplate!!)
    }

    private fun clearResources() {
        state.tempFile?.delete()
        state.documentTemplate?.uploadedDocument?.file?.finalizeOperation()
    }
}

private class State {
    var variableNamesExtractedFromRequest: MutableSet<String>? = null
    var tempFile: File? = null
    lateinit var wrappedParams: DocumentTemplateRequestParametersWrapper
    var documentTemplate: DocumentTemplate? = null
    lateinit var nameToLinksMap: MutableMap<String, MutableList<DocumentTemplateToDocumentVariableLink>>
}
