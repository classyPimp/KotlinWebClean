package composers.persontocounterpartylinkreasons

import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReason
import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReasonRequestParametersWrapper
import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReasonValidator
import models.persontocounterpartylinkreason.daos.PersonToCounterPartyLinkReasonDaos
import models.persontocounterpartylinkreason.updaters.PersonToCounterPartyLinkReasonUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class Update(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (PersonToCounterPartyLinkReason)->Unit
    lateinit var onError: (PersonToCounterPartyLinkReason)->Unit

    lateinit var linkReasonBeingUpdate: PersonToCounterPartyLinkReason
    lateinit var wrappedParams: PersonToCounterPartyLinkReasonRequestParametersWrapper

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError())
        prepareWrappedParams()
        findAndSetLinkReasonBeingUpdate()
        update()
        validate()
    }

    private fun prepareWrappedParams() {
        params.get("personToCounterPartyLinkReason")?.let {
            wrappedParams = PersonToCounterPartyLinkReasonRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun findAndSetLinkReasonBeingUpdate() {
        PersonToCounterPartyLinkReasonDaos.edit.findDefaultForEdit(id!!)?.let {
            linkReasonBeingUpdate = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun update() {
        PersonToCounterPartyLinkReasonUpdaters.defaultUpdater.update(linkReasonBeingUpdate, wrappedParams)
    }

    private fun validate() {
        PersonToCounterPartyLinkReasonValidator(linkReasonBeingUpdate).updateScenario()
        if (!linkReasonBeingUpdate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError(""))
        }
    }

    override fun compose(){
        linkReasonBeingUpdate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                PersonToCounterPartyLinkReason().let {
                    it.record.validationManager.addGeneralError("could not be found")
                    onError(it)
                }
            }
            is ModelInvalidError -> {
                onError(linkReasonBeingUpdate)
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

