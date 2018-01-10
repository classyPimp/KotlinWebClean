package composers.persontocounterpartylinktouploadeddoclinkreasons

import composers.contacttypes.Update
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReasonValidator
import models.persontocounterpartylinktouploadeddoclinkreason.daos.PersonToCounterPartyLinkToUploadedDocLinkReasonDaos
import models.persontocounterpartylinktouploadeddoclinkreason.updaters.PersonToCounterPartyLinkToUploadedDocLinkReasonUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class Update(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (PersonToCounterPartyLinkToUploadedDocLinkReason)->Unit
    lateinit var onError: (PersonToCounterPartyLinkToUploadedDocLinkReason)->Unit

    lateinit var linkReasonBeingUpdate: PersonToCounterPartyLinkToUploadedDocLinkReason
    lateinit var wrappedParams: PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper

    override fun beforeCompose(){
        id ?: failImmediately(UnprocessableEntryError())
        wrapParams()
        findAndSetLinkReasonBeingUpdate()
        runUpdate()
        validate()
    }

    private fun wrapParams() {
        params.get("personToCounterPartyLinkToUploadedDocLinkReason")?.let {
            wrappedParams = PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
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
            failImmediately(ModelInvalidException())
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
            is ModelInvalidException -> {
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

