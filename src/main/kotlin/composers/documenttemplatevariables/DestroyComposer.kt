package composers.documenttemplatevariables

import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.daos.DocumentTemplateVariableDaos
import orm.modelUtils.exceptions.ModelNotFoundError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError

class DestroyComposer(val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (DocumentTemplateVariable)->Unit
    lateinit var onError: (DocumentTemplateVariable)->Unit

    lateinit var documentTemplateVariableToDestroy: DocumentTemplateVariable

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError())
        findAndSetdocumentTemplateVariableToDestroy()
    }

    private fun findAndSetdocumentTemplateVariableToDestroy() {
        DocumentTemplateVariableDaos.show.forDestroyById(id!!)?.let {
            documentTemplateVariableToDestroy = it
        } ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        documentTemplateVariableToDestroy.record.delete()
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
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(documentTemplateVariableToDestroy)
    }

}

