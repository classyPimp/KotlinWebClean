package composers.persontocounterpartylinkreasons

import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReason
import models.persontocounterpartylinkreason.daos.PersonToCounterPartyLinkReasonDaos
import orm.modelUtils.exceptions.ModelNotFoundError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError

class Destroy(val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (PersonToCounterPartyLinkReason)->Unit
    lateinit var onNotFound: ()->Unit

    lateinit var linkReasonBeingDestroyed: PersonToCounterPartyLinkReason


    override fun beforeCompose(){
        id ?: failImmediately(UnprocessableEntryError())
        findAndSetLinkReasonBeignDestroyed()
    }

    private fun findAndSetLinkReasonBeignDestroyed() {
        PersonToCounterPartyLinkReasonDaos.show.findById(id!!)?.let {
            linkReasonBeingDestroyed = it
        } ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        linkReasonBeingDestroyed.record.delete()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onNotFound()
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(linkReasonBeingDestroyed)
    }

}

