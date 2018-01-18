package composers.documenttemplatevariables

import models.documenttemplatevariable.DocumentTemplateVariable
import models.documenttemplatevariable.DocumentTemplateVariableRequestParametersWrapper
import models.documenttemplatevariable.factories.DocumentTemplateVariableFactories
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
        //validate()
    }

    private fun wrapParams() {
        params.get("documentTemplateVariable")?.let {
            wrappedParams = DocumentTemplateVariableRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    fun build(){
        documentTemplateVariableToCreate = DocumentTemplateVariableFactories.defaultCreate.create(wrappedParams)
    }

    override fun compose(){

    }

    override fun fail(error: Throwable) {
        when(error) {

            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(documentTemplateVariableToCreate)
    }

}

