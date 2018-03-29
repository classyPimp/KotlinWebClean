package composers.persontocounterpartylinktouploadeddoclinkreasons

import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason
import models.persontocounterpartylinktouploadeddoclinkreason.daos.PersonToCounterPartyLinkToUploadedDocLinkReasonDaos
import orm.modelUtils.exceptions.ModelNotFoundError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError

class Destroy(val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (PersonToCounterPartyLinkToUploadedDocLinkReason)->Unit
    lateinit var onError: (PersonToCounterPartyLinkToUploadedDocLinkReason)->Unit

    lateinit var linkReasonBeingDestroyed: PersonToCounterPartyLinkToUploadedDocLinkReason

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError())
        findAndSetLinkReasonBeingDestroyed()
    }

    private fun findAndSetLinkReasonBeingDestroyed() {
        PersonToCounterPartyLinkToUploadedDocLinkReasonDaos.destroy.getForDestroy(id!!)?.let {
            linkReasonBeingDestroyed = it
        } ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        linkReasonBeingDestroyed.record.delete()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        PersonToCounterPartyLinkToUploadedDocLinkReason().also {
                            it.record.validationManager.addGeneralError("can't delete no such link reason")
                        }
                )
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

