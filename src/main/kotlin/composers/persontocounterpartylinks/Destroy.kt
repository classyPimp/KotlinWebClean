package composers.persontocounterpartylinks

import models.persontocounterpartylink.PersonToCounterPartyLink
import models.persontocounterpartylink.daos.PersonToCounterPartyLinkDaos
import orm.modelUtils.exceptions.ModelNotFoundError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError

class Destroy(val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (PersonToCounterPartyLink)->Unit
    lateinit var onError: (PersonToCounterPartyLink)->Unit

    lateinit var linkBeingDestroyed: PersonToCounterPartyLink

    override fun beforeCompose(){
        id ?: failImmediately(UnprocessableEntryError())
        findAndSetLinkBeingDestroyed()
    }

    private fun findAndSetLinkBeingDestroyed(){
        PersonToCounterPartyLinkDaos.show.getByIdforDestroy(id!!)?.let {
            linkBeingDestroyed = it
        } ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        linkBeingDestroyed.record.delete()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        PersonToCounterPartyLink().also {
                            it.record.validationManager.addGeneralError("can't delete: no such link exists")
                        }
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(linkBeingDestroyed)
    }

}

