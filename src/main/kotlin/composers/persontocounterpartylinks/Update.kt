package composers.persontocounterpartylinks

import models.persontocounterpartylink.PersonToCounterPartyLink
import models.persontocounterpartylink.PersonToCounterPartyLinkRequestParametersWrapper
import models.persontocounterpartylink.PersonToCounterPartyLinkValidator
import models.persontocounterpartylink.daos.PersonToCounterPartyLinkDaos
import models.persontocounterpartylink.updaters.PersonToCounterPartyLinkUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class Update(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (PersonToCounterPartyLink)->Unit
    lateinit var onError: (PersonToCounterPartyLink)->Unit

    lateinit var wrappedParams: PersonToCounterPartyLinkRequestParametersWrapper
    lateinit var linkBeingUpdated: PersonToCounterPartyLink

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError())
        wrapParams()
        findAndSetLinkBeingUpdate()
        runUpdater()
        preloadAssociatedRequiredForValidationIfRequired()
        validate()
    }

    private fun wrapParams() {
        params.get("personToCounterPartyLink")?.let {
            wrappedParams = PersonToCounterPartyLinkRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun findAndSetLinkBeingUpdate(){
        PersonToCounterPartyLinkDaos.edit.getForUpdateById(id!!)?.let {
            linkBeingUpdated = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun runUpdater() {
        PersonToCounterPartyLinkUpdaters.default.update(linkBeingUpdated, wrappedParams)
    }

    private fun preloadAssociatedRequiredForValidationIfRequired() {
        if (linkBeingUpdated.record.propertiesChangeTracker.personToCounterPartyLinkReasonIdIsChanged) {
            linkBeingUpdated.record.loadPersonToCounterPartyLinkReason()
        }
    }

    private fun validate() {
        PersonToCounterPartyLinkValidator(linkBeingUpdated).updateScenario()
        if (!linkBeingUpdated.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        linkBeingUpdated.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        PersonToCounterPartyLink().also {
                            it.record.validationManager.addGeneralError("no such link")
                        }
                )
            }
            is ModelInvalidError -> {
                onError(
                        linkBeingUpdated
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(linkBeingUpdated)
    }

}

