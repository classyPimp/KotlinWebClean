package composers.persontocounterpartylinks

import models.persontocounterpartylink.PersonToCounterPartyLink
import models.persontocounterpartylink.PersonToCounterPartyLinkRequestParametersWrapper
import models.persontocounterpartylink.PersonToCounterPartyLinkValidator
import models.persontocounterpartylink.factories.PersonToCounterPartyLinkFactories
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class Create(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (PersonToCounterPartyLink)->Unit
    lateinit var onError: (PersonToCounterPartyLink)->Unit

    lateinit var wrappedParams: PersonToCounterPartyLinkRequestParametersWrapper
    lateinit var linkBeingCreated: PersonToCounterPartyLink

    override fun beforeCompose(){
        wrapParams()
        buildLinkBeingCreated()
        preloadAssociatedRequiredForValidation()
        validate()
    }

    private fun wrapParams(){
        params.get("personToCounterPartyLink")?.let {
            wrappedParams = PersonToCounterPartyLinkRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun buildLinkBeingCreated(){
        linkBeingCreated = PersonToCounterPartyLinkFactories.defaultCreate.run(wrappedParams)
    }

    private fun preloadAssociatedRequiredForValidation(){
        linkBeingCreated.record.let {
            it.loadCounterParty()
            it.loadPerson()
            it.loadPersonToCounterPartyLinkReason()
        }
    }

    private fun validate(){
        PersonToCounterPartyLinkValidator(linkBeingCreated).createScenario()
        if (!linkBeingCreated.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        linkBeingCreated.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(
                        linkBeingCreated
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(linkBeingCreated)
    }

}

