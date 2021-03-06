package composers.persontocounterpartylinkreasons

import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReason
import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReasonRequestParametersWrapper
import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReasonValidator
import models.persontocounterpartylinkreason.factories.PersonToCounterPartyLinkReasonFactories
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class Create(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (PersonToCounterPartyLinkReason)->Unit
    lateinit var onError: (PersonToCounterPartyLinkReason)->Unit

    lateinit var linkReasonBeingCreated: PersonToCounterPartyLinkReason
    lateinit var wrappedParams: PersonToCounterPartyLinkReasonRequestParametersWrapper

    override fun beforeCompose(){
        prepareWrappedParams()
        buildLinkReason()
        validate()
    }

    private fun prepareWrappedParams() {
        params.get("personToCounterPartyLinkReason")?.let {
            wrappedParams = PersonToCounterPartyLinkReasonRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun buildLinkReason() {
        linkReasonBeingCreated = PersonToCounterPartyLinkReasonFactories.create.create(wrappedParams)
    }

    private fun validate() {
        PersonToCounterPartyLinkReasonValidator(linkReasonBeingCreated).createScenario()
        if (!linkReasonBeingCreated.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError(""))
        }
    }

    override fun compose(){
        linkReasonBeingCreated.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(linkReasonBeingCreated)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(linkReasonBeingCreated)
    }

}

