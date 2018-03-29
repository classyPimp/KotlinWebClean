package composers.persontocounterpartylinktouploadeddoclinkreasons

import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReasonValidator
import models.persontocounterpartylinktouploadeddoclinkreason.daos.PersonToCounterPartyLinkToUploadedDocLinkReasonDaos
import models.persontocounterpartylinktouploadeddoclinkreason.updaters.PersonToCounterPartyLinkToUploadedDocLinkReasonUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class Update(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (PersonToCounterPartyLinkToUploadedDocLinkReason)->Unit
    lateinit var onError: (PersonToCounterPartyLinkToUploadedDocLinkReason)->Unit

    lateinit var linkReasonBeingUpdate: PersonToCounterPartyLinkToUploadedDocLinkReason
    lateinit var wrappedParams: PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError())
        wrapParams()
        findAndSetLinkReasonBeingUpdate()
        runUpdate()
        validate()
    }

    private fun wrapParams() {
        params.get("personToCounterPartyLinkToUploadedDocLinkReason")?.let {
            wrappedParams = PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun findAndSetLinkReasonBeingUpdate() {
        PersonToCounterPartyLinkToUploadedDocLinkReasonDaos.edit.byId(id!!)?.let {
            linkReasonBeingUpdate = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun runUpdate(){
        PersonToCounterPartyLinkToUploadedDocLinkReasonUpdaters.defaultUpdater.update(linkReasonBeingUpdate, wrappedParams)
    }

    private fun validate() {
        PersonToCounterPartyLinkToUploadedDocLinkReasonValidator(linkReasonBeingUpdate).updateScenario()
        if (!linkReasonBeingUpdate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        linkReasonBeingUpdate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        PersonToCounterPartyLinkToUploadedDocLinkReason().also {
                            it.record.validationManager.addGeneralError("no such link reason")
                        }
                )
            }
            is ModelInvalidError -> {
                onError(
                        linkReasonBeingUpdate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(linkReasonBeingUpdate)
    }

}

