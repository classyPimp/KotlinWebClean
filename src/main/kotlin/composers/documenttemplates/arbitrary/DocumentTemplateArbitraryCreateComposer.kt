package composers.documenttemplates.arbitrary

import docxtemplating.DocxTemplateVariablesHandler
import models.documenttemplate.DocumentTemplate
import models.documenttemplate.DocumentTemplateRequestParametersWrapper
import models.documenttemplate.DocumentTemplateValidator
import models.documenttemplate.daos.DocumentTemplateDaos
import models.documenttemplate.factories.DocumentTemplateFactories
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.fileutils.FileNamingUtils
import utils.requestparameters.IParam
import java.io.File
import java.nio.file.Files

class DocumentTemplateArbitraryCreateComposer(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (File)->Unit
    lateinit var onError: (DocumentTemplate)->Unit

    lateinit var sourceDocumentTemplate: DocumentTemplate
    lateinit var providedDocumentTemplate: DocumentTemplate
    lateinit var wrappedParams: DocumentTemplateRequestParametersWrapper
    var nameWithIdentifierToLinkMap = mutableMapOf<String, DocumentTemplateToDocumentVariableLink>()
    lateinit var templateHandler: DocxTemplateVariablesHandler

    val providedLinksIdToLinkMap = mutableMapOf<Long, DocumentTemplateToDocumentVariableLink>()
    val sourceLinksIdToLinkMap = mutableMapOf<Long, DocumentTemplateToDocumentVariableLink>()

    override fun beforeCompose(){
        wrapParams()
        instantiateProvidedDocumentTemplate()
        findAndSetSourceDocumentTemplate()
        validateLinksIntegrity()
        buildNameWithIdentifierToLinkMap()
        initializeTemplateHandler()
        prevalidateAgainstTemplate()
        runFinalValidation()
    }

    private fun wrapParams() {
        params.get("documentTemplate")?.let {
            wrappedParams = DocumentTemplateRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    private fun instantiateProvidedDocumentTemplate() {
        providedDocumentTemplate = DocumentTemplateFactories.defaultCreate.create(wrappedParams)
        if (providedDocumentTemplate.id == null) {
            failImmediately(UnprocessableEntryError())
            return
        }
        providedDocumentTemplate.documentTemplateToDocumentVariableLinks = providedDocumentTemplate.documentTemplateToDocumentVariableLinks ?: mutableListOf()
    }

    private fun findAndSetSourceDocumentTemplate() {
        DocumentTemplateDaos.show.forArbitraryCreate(providedDocumentTemplate.id!!)?.let {
            sourceDocumentTemplate = it
            sourceDocumentTemplate.documentTemplateToDocumentVariableLinks = sourceDocumentTemplate.documentTemplateToDocumentVariableLinks ?: mutableListOf()
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun validateLinksIntegrity() {
        val providedLinks = providedDocumentTemplate.documentTemplateToDocumentVariableLinks!!
        val sourceLinks = sourceDocumentTemplate.documentTemplateToDocumentVariableLinks!!

        providedLinks.forEach {
            providedLinksIdToLinkMap[it.id!!] = it
        }
        sourceLinks.forEach {
            sourceLinksIdToLinkMap[it.id!!] = it
        }

        validateIfSourcedExceedsProvided()
        validateIfProvidedExceedsSourced()
        validateEqualityOfSourcedAndProvided()

    }

    private fun validateIfSourcedExceedsProvided() {
        val sourceIds = sourceLinksIdToLinkMap.keys
        val providedIds = providedLinksIdToLinkMap.keys
        val difference = sourceIds.minus(providedIds)
        if (difference.isNotEmpty()) {
            difference.forEach {
                providedLinksIdToLinkMap[it] = sourceLinksIdToLinkMap[it]!!.also {
                    it.documentTemplateVariable!!.record.validationManager.addGeneralError("should be provided")
                }
            }
        }
    }

    private fun validateIfProvidedExceedsSourced() {
        val sourceIds = sourceLinksIdToLinkMap.keys
        val providedIds = providedLinksIdToLinkMap.keys
        val difference = providedIds.minus(sourceIds)
        if (difference.isNotEmpty()) {
            difference.forEach {
                val providedLink = providedLinksIdToLinkMap[it]!!
                providedLink.documentTemplateVariable!!.record.validationManager.addGeneralError("no such variable in template")
            }
        }
    }

    private fun validateEqualityOfSourcedAndProvided() {
        for (entry in sourceLinksIdToLinkMap) {
            val id = entry.key
            val sourceLink = entry.value
            val providedLink = providedLinksIdToLinkMap[id]!!
            if (sourceLink.id != providedLink.id) {
                providedLink.documentTemplateVariable!!.record.validationManager.addGeneralError("invalid: ${sourceLink.id} != ${providedLink.id}")
            }
            if (sourceLink.documentTemplateVariableId != providedLink.documentTemplateVariableId) {
                providedLink.documentTemplateVariable!!.record.validationManager.addGeneralError("invalid: ${sourceLink.documentTemplateVariableId} != ${providedLink.documentTemplateVariableId}")
            }
            if (sourceLink.documentTemplateVariable!!.id != providedLink.documentTemplateVariable!!.id) {
                providedLink.documentTemplateVariable!!.record.validationManager.addGeneralError("invalid: ${sourceLink.documentTemplateVariable!!.id} != ${providedLink.documentTemplateVariable!!.id}")
            }
        }
    }

    private fun buildNameWithIdentifierToLinkMap() {
        providedDocumentTemplate.documentTemplateToDocumentVariableLinks!!.forEach {
            val nameWithIdentifier = if (it.uniqueIdentifier != null) {
                "${it.documentTemplateVariable?.name}-${it.uniqueIdentifier}"
            } else {
                it.documentTemplateVariable?.name!!
            }
            nameWithIdentifierToLinkMap[nameWithIdentifier] = it
        }
    }

    private fun initializeTemplateHandler() {
        val file = sourceDocumentTemplate.uploadedDocument!!.file.getFileItself()
        if (file == null) {
            failImmediately(UnprocessableEntryError())
            return
        }
        templateHandler = DocxTemplateVariablesHandler(file)
    }

    private fun prevalidateAgainstTemplate() {
        val variablesOnTemplate = templateHandler.extractVariableNamesWithIdentifiersAsSet()
        validateIfTemplateVarsMoreThanProvided(variablesOnTemplate)
        validateIfProvidedVarsMoreThanInTemplate(variablesOnTemplate)
    }

    private fun validateIfTemplateVarsMoreThanProvided(variableNamesOnTemplate: MutableSet<String>) {
        val providedVariableNames = nameWithIdentifierToLinkMap.keys
        val difference = variableNamesOnTemplate.minus(providedVariableNames)
        if (difference.isNotEmpty()) {
            difference.forEach {
                providedDocumentTemplate.record.validationManager.addGeneralError("template invalid: erronous variable: ${it} - it is not on template bu was provided")
            }
        }
    }

    private fun validateIfProvidedVarsMoreThanInTemplate(variableNamesOnTemplate: MutableSet<String>) {
        val providedVariableNames = nameWithIdentifierToLinkMap.keys
        val difference = providedVariableNames.minus(variableNamesOnTemplate)
        if (difference.isNotEmpty()) {
            difference.forEach {
                providedDocumentTemplate.record.validationManager.addGeneralError("template invalid: erronous variable: ${it} - it is in template but not provided")
            }
        }
    }

    fun runFinalValidation() {
        DocumentTemplateValidator(providedDocumentTemplate).arbitraryCreateScenario()
        if (!providedDocumentTemplate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        templateHandler.replaceVariables(nameWithIdentifierToLinkMap)
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        providedDocumentTemplate.also {
                            it.record.validationManager.addGeneralError("no such template")
                        }
                )
            }
            is ModelInvalidException -> {
                onError(
                        providedDocumentTemplate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        val file = Files.createTempFile(FileNamingUtils.generateUniqueFileName(), ".docx").toFile()
        try {
            templateHandler.template.save(file)
        } catch (error: Exception) {
            file?.delete()
        }
        try {
            onSuccess(file)
        } finally {
            file?.delete()
        }
    }

}

