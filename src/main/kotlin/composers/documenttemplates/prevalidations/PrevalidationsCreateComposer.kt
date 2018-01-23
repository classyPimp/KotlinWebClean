package composers.documenttemplates.prevalidations

import docxtemplating.DocxTemplateVariablesExtractionProcedure
import models.documenttemplate.DocumentTemplate
import models.documenttemplate.DocumentTemplateRequestParametersWrapper
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.daos.DocumentTemplateVariableDaos
import orm.services.ModelInvalidException
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

    val estimatedVariableLinks: MutableList<DocumentTemplateToDocumentVariableLink> = mutableListOf()
    lateinit var persistedDocumentTemplateVariablesNameToDocumentTemplateVariableMap: MutableMap<String, DocumentTemplateVariable>

    override fun beforeCompose(){
        params.get("documentTemplate")?.let {
            wrappedParams = DocumentTemplateRequestParametersWrapper(params)
        } ?: failImmediately(UnprocessableEntryError())

        documentTemplate.documentTemplateToDocumentTemplateVariableLinks = estimatedVariableLinks

        try {
            createTempfileForValidation()
            prepareEstimatedVariableLinks()
        } finally {
            tempFile?.delete()
        }

        if (estimatedVariableLinks.isEmpty()) {
            return
        }

        validate()
    }



    private fun createTempfileForValidation() {
        try {
            tempFile = createTempFile(FileNamingUtils.generateUniqueFileName())
        } catch(error: Exception) {
            documentTemplate.record.validationManager.addGeneralError("file invalid")
            failImmediately(ModelInvalidException())
        }
    }

    private fun prepareEstimatedVariableLinks() {
        var extractedVariables: MutableMap<String, DocumentTemplateVariable?>
        try {
             extractedVariables = DocxTemplateVariablesExtractionProcedure(tempFile!!).execute()
        } catch (error: Exception) {
            documentTemplate.record.validationManager.addGeneralError("file invalid")
            failImmediately(ModelInvalidException())
            return
        }

        val nameToLinkMap = mutableMapOf<String, DocumentTemplateToDocumentVariableLink>()
        val namesByWhichToQueryDocumentTemplateVariables = mutableSetOf<String>()

        for (it in extractedVariables.keys) {
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
                link.documentTemplateVariableId = it.id
                link.documentTemplateVariable = it
                link.defaultValue = it.defaultValue
            }
        }

    }

    private fun validate() {
        var hasError = false

        estimatedVariableLinks.forEach {
            if (it.documentTemplateVariable == null) {
                hasError = true
                it.record.validationManager.addGeneralError("does not exist in database")
            }
        }

        if (hasError) {
            documentTemplate.record.validationManager.addGeneralError("contains invalid links")
        }

        if (!documentTemplate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }


    override fun compose(){

    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidException -> {
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
