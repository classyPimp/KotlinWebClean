package composers.documenttemplates.prevalidations

import docxtemplating.DocxTemplateVariablesHandler
import models.documenttemplate.DocumentTemplate
import models.documenttemplate.DocumentTemplateRequestParametersWrapper
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.daos.DocumentTemplateVariableDaos
import orm.services.ModelInvalidError
import utils.fileutils.FileNamingUtils
import java.io.File


/**
 * Created by Муса on 23.01.2018.
 */
class PrevalidationsCreateComposer(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (DocumentTemplate)->Unit
    lateinit var onError: (DocumentTemplate)->Unit

    val documentTemplate = DocumentTemplate()
    lateinit var wrappedParams: DocumentTemplateRequestParametersWrapper
    var tempFile: File? = null


    lateinit var persistedDocumentTemplateVariablesNameToDocumentTemplateVariableMap: MutableMap<String, DocumentTemplateVariable>

    override fun beforeCompose(){

        params.get("documentTemplate")?.let {
            wrappedParams = DocumentTemplateRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())

        try {
            createTempfileForValidation()
            prepareEstimatedVariableLinks()
        } finally {
            tempFile?.delete()
            wrappedParams.uploadedDocument?.file?.delete()
        }

        validate()
    }



    private fun createTempfileForValidation() {
        try {
            tempFile = createTempFile(FileNamingUtils.generateUniqueFileName())

            wrappedParams.uploadedDocument?.file?.let {
                it.write(tempFile)
            } ?: throw(Throwable("no file in params"))
        } catch(error: Exception) {
            throw(error)
            documentTemplate.record.validationManager.addGeneralError("file invalid")
            failImmediately(ModelInvalidError())
        }
    }

    private fun prepareEstimatedVariableLinks() {
        var extractedVariables: MutableSet<String>
        try {
             extractedVariables = DocxTemplateVariablesHandler(tempFile!!).extractVariableNamesAsSet()
        } catch (error: Exception) {
            throw (error)
            documentTemplate.record.validationManager.addGeneralError("file invalid")
            failImmediately(ModelInvalidError())
            return
        }

        val nameToLinkMap = mutableMapOf<String, DocumentTemplateToDocumentVariableLink>()
        val namesByWhichToQueryDocumentTemplateVariables = mutableSetOf<String>()

        for (it in extractedVariables) {
            if (it.isNullOrBlank()) {
                nameToLinkMap[""] = DocumentTemplateToDocumentVariableLink().also {
                    documentTemplate.record.validationManager.addGeneralError("some variables are blank")
                }
                continue
            }
            val splitted = it.split('-')
            val name = splitted.get(0)
            val identifier = splitted.getOrNull(1)

            nameToLinkMap[name] = DocumentTemplateToDocumentVariableLink().also {
                if (identifier != null) {
                    it.uniqueIdentifier = identifier
                }
            }

            namesByWhichToQueryDocumentTemplateVariables.add(name)
        }

        val documentTemplateVariables = DocumentTemplateVariableDaos.index.forDocumentTemplatePrevalidationsCreate(namesByWhichToQueryDocumentTemplateVariables)

        documentTemplateVariables.forEach {
            val link = nameToLinkMap[it.name]
            if (link != null) {
                link.documentTemplateVariable = it
                link.documentTemplateVariableId = it.id
                link.defaultValue = it.defaultValue
            }
        }


        nameToLinkMap.forEach { key, value ->
            if (value.documentTemplateVariableId == null) {
                value.documentTemplateVariable = DocumentTemplateVariable().also {
                    it.name = key
                    it.record.validationManager.addGeneralError("no ${key} variable in database: create it first")
                }
            }

        }

        nameToLinkMap.values.let {
            documentTemplate.documentTemplateToDocumentVariableLinks = it.toMutableList()
        }

        println(nameToLinkMap)

    }

    private fun validate() {
        var hasError = false

        documentTemplate.documentTemplateToDocumentVariableLinks?.forEach {
            val documentTemplateVariable = it.documentTemplateVariable
            if (documentTemplateVariable != null) {
                if (!documentTemplateVariable.record.validationManager.isValid()) {
                    hasError = true
                    it.record.validationManager.addGeneralError("does not exist in database")
                }
            }
            if (!it.record.validationManager.isValid()) {
                hasError = true
            }
        }

        if (hasError) {
            documentTemplate.record.validationManager.addGeneralError("contains invalid links")
        }

        if (!documentTemplate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }


    override fun compose(){

    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(
                        documentTemplate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(documentTemplate)
    }

}
