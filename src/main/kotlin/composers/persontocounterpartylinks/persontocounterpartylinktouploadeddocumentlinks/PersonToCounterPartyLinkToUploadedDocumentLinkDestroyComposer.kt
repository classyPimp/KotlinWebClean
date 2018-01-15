package composers.persontocounterpartylinks.persontocounterpartylinktouploadeddocumentlinks

import utils.composer.ComposerBase

class PersonToCounterPartyLinkToUploadedDocumentLinkDestroyComposer(
        val personToCounterPartyLinkId: Long?,
        val id: Long?
) : ComposerBase() {

    lateinit var onSuccess: ()->Unit
    lateinit var onError: ()->Unit

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
        onSuccess.invoke()
    }

}

