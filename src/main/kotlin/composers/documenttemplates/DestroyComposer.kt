package composers.documenttemplates

import models.documenttemplate.DocumentTemplate
import utils.composer.ComposerBase

class DestroyComposer(val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (DocumentTemplate)->Unit
    lateinit var onError: (DocumentTemplate)->Unit

    lateinit var documentTemplateToDestroy: DocumentTemplate

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
        onSuccess.invoke(documentTemplateToDestroy)
    }

}

