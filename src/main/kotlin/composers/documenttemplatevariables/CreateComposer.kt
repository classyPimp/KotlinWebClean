package composers.documenttemplatevariables

import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.DocumentTemplateVariableRequestParametersWrapper
import models.documenttemplatevariable.DocumentTemplateVariableValidator
import models.documenttemplatevariable.factories.DocumentTemplateVariableFactories
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
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
        } ?: failImmediately(UnprocessableEntryError())
    }

    fun build(){
        documentTemplateVariableToCreate = DocumentTemplateVariableFactories.defaultCreate.create(wrappedParams)
    }

    private fun validate() {
        DocumentTemplateVariableValidator(documentTemplateVariableToCreate).createScenario()
        if (!documentTemplateVariableToCreate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        documentTemplateVariableToCreate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidException -> {
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

