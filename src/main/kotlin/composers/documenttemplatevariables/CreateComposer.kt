package composers.documenttemplatevariables

import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.DocumentTemplateVariableRequestParametersWrapper
import models.documenttemplatevariable.DocumentTemplateVariableValidator
import models.documenttemplatevariable.factories.DocumentTemplateVariableFactories
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class CreateComposer(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (DocumentTemplateVariable)->Unit
    lateinit var onError: (DocumentTemplateVariable)->Unit

    lateinit var documentTemplateVariableToCreate: DocumentTemplateVariable
    lateinit var wrappedParams: DocumentTemplateVariableRequestParametersWrapper

    override fun beforeCompose(){
        wrapParams()
        build()
        validate()
    }

    private fun wrapParams() {
        params.get("documentTemplateVariable")?.let {
            wrappedParams = DocumentTemplateVariableRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    fun build(){
        documentTemplateVariableToCreate = DocumentTemplateVariableFactories.defaultCreate.create(wrappedParams)
    }

    private fun validate() {
        DocumentTemplateVariableValidator(documentTemplateVariableToCreate).createScenario()
        if (!documentTemplateVariableToCreate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        documentTemplateVariableToCreate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
               onError(
                       documentTemplateVariableToCreate
               )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(documentTemplateVariableToCreate)
    }

}

