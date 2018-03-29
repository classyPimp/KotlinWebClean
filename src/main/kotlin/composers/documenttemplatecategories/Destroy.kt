package composers.documenttemplatecategories

import models.documenttemplatecategory.DocumentTemplateCategory
import models.documenttemplatecategory.daos.DocumentTemplateCategoryDaos
import orm.modelUtils.exceptions.ModelNotFoundError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError

class Destroy(val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (DocumentTemplateCategory)->Unit
    lateinit var onError: (DocumentTemplateCategory)->Unit

    lateinit var documentTemplateCategoryToDestroy: DocumentTemplateCategory

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError())
        findAndSetDocumentTemplateCategoryToDestroy()
    }

    private fun findAndSetDocumentTemplateCategoryToDestroy() {
        DocumentTemplateCategoryDaos.show.forDestroy(id!!)?.let {
            documentTemplateCategoryToDestroy = it
        } ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        documentTemplateCategoryToDestroy.record.delete()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        DocumentTemplateCategory().also {
                            it.record.validationManager.addGeneralError("no such category in database")
                        }
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(documentTemplateCategoryToDestroy)
    }

}

