package composers.documenttemplatevariables

import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.DocumentTemplateVariableRequestParametersWrapper
import models.documenttemplatevariable.DocumentTemplateVariableValidator
import models.documenttemplatevariable.daos.DocumentTemplateVariableDaos
import models.documenttemplatevariable.updaters.DocumentTemplateVariableUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class UpdateComposer(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (DocumentTemplateVariable)->Unit
    lateinit var onError: (DocumentTemplateVariable)->Unit

    lateinit var documentTemplateVariableToUpdate: DocumentTemplateVariable
    lateinit var wrappedParams: DocumentTemplateVariableRequestParametersWrapper

    override fun beforeCompose(){
        id ?: failImmediately(UnprocessableEntryError())
        wrapParams()
        findAndSetDocumentTemplateVariableToUpdate()
        runUpdater()
        validate()
    }

    private fun wrapParams(){
        params.get("documentTemplateVariable")?.let {
            wrappedParams = DocumentTemplateVariableRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    private fun findAndSetDocumentTemplateVariableToUpdate() {
        DocumentTemplateVariableDaos.show.forUpdate(id = id!!)?.let {
            documentTemplateVariableToUpdate = it
       } ?: failImmediately(ModelNotFoundError())
    }

    private fun runUpdater(){
        DocumentTemplateVariableUpdaters.defaultUpdater.update(documentTemplateVariableToUpdate, wrappedParams)
    }

    private fun validate() {
        DocumentTemplateVariableValidator(documentTemplateVariableToUpdate).updateScenario()
        if (!documentTemplateVariableToUpdate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        documentTemplateVariableToUpdate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        DocumentTemplateVariable().also {
                            it.record.validationManager.addGeneralError("no such template variable in database")
                        }
                )
            }
            is ModelInvalidException -> {
                onError(
                        documentTemplateVariableToUpdate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(documentTemplateVariableToUpdate)
    }

}

