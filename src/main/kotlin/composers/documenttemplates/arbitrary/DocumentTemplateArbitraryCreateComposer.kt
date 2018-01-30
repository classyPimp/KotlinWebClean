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

    override fun beforeCompose(){
        wrapParams()
        instantiateProvidedDocumentTemplate()
        findAndSetSourceDocumentTemplate()
        validateLinksIntegrity()
        buildNameWithIdentifierToLinkMap()
        initializeTemplateHandler()
        prevalidateAgainstTemplate()
        validate()
    }

    private fun wrapParams() {
        params.get("documentTemplate")?.let {
            wrappedParams = DocumentTemplateRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    private fun instantiateProvidedDocumentTemplate() {
        providedDocumentTemplate = DocumentTemplateFactories.defaultCreate.create(wrappedParams)
        val id = params.get("documentTemplate")?.let {
            it.get("id")?.string?.toLongOrNull()
        }
        if (id == null) {
            failImmediately(UnprocessableEntryError())
            return
        }
        providedDocumentTemplate.id = id
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

        val providedLinksIdToLinkMap = mutableMapOf<Long, DocumentTemplateToDocumentVariableLink>()
        val sourceLinksIdToLinkMap = mutableMapOf<Long, DocumentTemplateToDocumentVariableLink>()

        providedLinks.forEach {
            providedLinksIdToLinkMap[it.id!!] = it
        }
        sourceLinks.forEach {
            sourceLinksIdToLinkMap[it.id!!] = it
        }

        for (entry in sourceLinksIdToLinkMap) {
            val id = entry.key
            val sourceLink = entry.value
            val providedLink = providedLinksIdToLinkMap[id]
            if (providedLink == null) {
                providedDocumentTemplate.record.validationManager.addGeneralError("no ${sourceLink.documentTemplateVariable?.name} variable provided")
                continue
            }
            if (providedLink.id != sourceLink.id) {
                providedLink.documentTemplateVariable!!.record.validationManager.addGeneralError("invalid link")
            }
            if (providedLink.documentTemplateId != sourceLink.documentTemplateId) {
                providedLink.documentTemplateVariable!!.record.validationManager.addGeneralError("invalid")
            }
        }

        if (providedLinksIdToLinkMap.keys.size != sourceLinksIdToLinkMap.keys.size) {
            val difference = providedLinksIdToLinkMap.keys.minus(sourceLinksIdToLinkMap.keys)
            difference.forEach {
                val providedLink = providedLinksIdToLinkMap[it]
                if (providedLink != null) {
                    providedLink.record.validationManager.addGeneralError("invalid")
                } else {
                    val sourceLink = sourceLinksIdToLinkMap[it]
                    providedDocumentTemplate.record.validationManager.addGeneralError("no variable ${sourceLink!!.documentTemplateVariable!!.name} provided")
                }
            }
        }
    }

    private fun buildNameWithIdentifierToLinkMap() {
        providedDocumentTemplate.documentTemplateToDocumentVariableLinks!!.forEach {
            val nameWithIdentifier = "${it.documentTemplateVariable?.name}-${it.uniqueIdentifier}"
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
        variablesOnTemplate.forEach {
            val link = nameWithIdentifierToLinkMap[it]
            if (link == null) {
                providedDocumentTemplate.record.validationManager.addGeneralError("no variable ${it.split('-')[0]} provided")
            } else {
                if (it != "${link.documentTemplateVariable!!.name}-${link.uniqueIdentifier}") {
                    link.documentTemplateVariable!!.record.validationManager.addGeneralError("invalid variable")
                }
            }
        }
    }

    fun validate() {
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
        var file: File? = null
        try {
            file = Files.createTempFile(FileNamingUtils.generateUniqueFileName(), "docx").toFile()
            templateHandler.template.save(file)
            onSuccess.invoke(file)
        } finally {
            file?.delete()
        }
    }

}

