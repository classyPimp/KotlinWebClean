package composers.documenttemplatecategories

import models.documenttemplatecategory.DocumentTemplateCategory
import models.documenttemplatecategory.DocumentTemplateCategoryRequestParametersWrapper
import models.documenttemplatecategory.DocumentTemplateCategoryValidator
import models.documenttemplatecategory.factories.DocumentTemplateCategoryFactories
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class Create(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (DocumentTemplateCategory)->Unit
    lateinit var onError: (DocumentTemplateCategory)->Unit

    lateinit var documentTemplateCategoryToCreate: DocumentTemplateCategory
    lateinit var wrappedParams: DocumentTemplateCategoryRequestParametersWrapper

    override fun beforeCompose(){
        wrapParams()
        build()
        validate()
    }

    private fun wrapParams() {
        params.get("documentTemplateCategory")?.let {
            wrappedParams = DocumentTemplateCategoryRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun build() {
        documentTemplateCategoryToCreate = DocumentTemplateCategoryFactories.defaultCreate.create(wrappedParams)
    }

    private fun validate() {
        DocumentTemplateCategoryValidator(documentTemplateCategoryToCreate).createScenario()
        if (!documentTemplateCategoryToCreate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        documentTemplateCategoryToCreate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(
                        documentTemplateCategoryToCreate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(documentTemplateCategoryToCreate)
    }

}

