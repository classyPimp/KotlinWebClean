package composers.documenttemplates

import models.documenttemplate.DocumentTemplate
import models.documenttemplate.DocumentTemplateRequestParametersWrapper
import utils.composer.ComposerBase
import utils.requestparameters.IParam

class UpdateComposer(val params: IParam, id: Long?) : ComposerBase() {

    lateinit var onSuccess: (DocumentTemplate)->Unit
    lateinit var onError: (DocumentTemplate)->Unit

    lateinit var documentTemplateToUpdate: DocumentTemplate
    lateinit var wrappedParams: DocumentTemplateRequestParametersWrapper

    override fun beforeCompose(){

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
        onSuccess.invoke(documentTemplateToUpdate)
    }

}

