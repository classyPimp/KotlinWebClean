package composers.documenttemplates

import models.documenttemplate.DocumentTemplate
import models.documenttemplate.daos.DocumentTemplateDaos
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.utils.TransactionRunner
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError

class DestroyComposer(val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (DocumentTemplate)->Unit
    lateinit var onError: (DocumentTemplate)->Unit

    lateinit var documentTemplateToDestroy: DocumentTemplate

    override fun beforeCompose(){
        id ?: failImmediately(UnprocessableEntryError())
        findAndSetDocumentTemplateToDestroy()
    }

    private fun findAndSetDocumentTemplateToDestroy() {
        DocumentTemplateDaos.show.forDestroy(id!!)?.let {
            documentTemplateToDestroy = it
        } ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        TransactionRunner.run {
            documentTemplateToDestroy.record.delete(
                    dslContext = it.inTransactionDsl,
                    before = {
                        documentTemplateToDestroy.uploadedDocument?.file?.delete()
                    },
                    after = {
                        documentTemplateToDestroy.uploadedDocument?.file?.finalizeOperation()
                    }
            )
        }
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        DocumentTemplate().also {
                            it.record.validationManager.addGeneralError("no such template")
                        }
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(documentTemplateToDestroy)
    }

}

