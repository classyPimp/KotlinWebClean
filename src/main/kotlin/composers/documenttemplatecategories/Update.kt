package composers.documenttemplatecategories

import models.documenttemplatecategory.DocumentTemplateCategory
import models.documenttemplatecategory.DocumentTemplateCategoryRequestParametersWrapper
import models.documenttemplatecategory.DocumentTemplateCategoryValidator
import models.documenttemplatecategory.daos.DocumentTemplateCategoryDaos
import models.documenttemplatecategory.updaters.DocumentTemplateCategoryUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class Update(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (DocumentTemplateCategory)->Unit
    lateinit var onError: (DocumentTemplateCategory)->Unit

    lateinit var documentTemplateCategoryToUpdate: DocumentTemplateCategory
    lateinit var wrappedParams: DocumentTemplateCategoryRequestParametersWrapper

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError())
        wrapParams()
        findAndSetDocumentTemplateCategoryToUpdate()
        runUpdater()
        validate()
    }

    private fun wrapParams() {
        params.get("documentTemplateCategory")?.let {
            wrappedParams = DocumentTemplateCategoryRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun findAndSetDocumentTemplateCategoryToUpdate() {
        DocumentTemplateCategoryDaos.show.forUpdate(id!!)?.let {
            documentTemplateCategoryToUpdate = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun runUpdater() {
        DocumentTemplateCategoryUpdaters.defaultUpdater.update(documentTemplateCategoryToUpdate, wrappedParams)
    }

    private fun validate() {
        DocumentTemplateCategoryValidator(documentTemplateCategoryToUpdate).updateScenario()
        if (!documentTemplateCategoryToUpdate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }
    override fun compose(){
        documentTemplateCategoryToUpdate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(
                        documentTemplateCategoryToUpdate
                )
            }
            is ModelNotFoundError -> {
                onError(
                        DocumentTemplateCategory().also {
                            it.record.validationManager.addGeneralError("can't update, not found in database")
                        }
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(documentTemplateCategoryToUpdate)
    }

}

