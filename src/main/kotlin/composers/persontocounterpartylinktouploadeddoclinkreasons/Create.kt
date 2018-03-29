package composers.persontocounterpartylinktouploadeddoclinkreasons

import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReasonValidator
import models.persontocounterpartylinktouploadeddoclinkreason.factories.PersonToCounterPartyLinkToUploadedDocLinkReasonFactories
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class Create(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (PersonToCounterPartyLinkToUploadedDocLinkReason)->Unit
    lateinit var onError: (PersonToCounterPartyLinkToUploadedDocLinkReason)->Unit

    lateinit var linkReasonBeingCreated: PersonToCounterPartyLinkToUploadedDocLinkReason
    lateinit var wrappedParams: PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper

    override fun beforeCompose(){
        wrapParams()
        build()
        validate()
    }

    private fun wrapParams(){
        params.get("personToCounterPartyLinkToUploadedDocLinkReason")?.let {
            wrappedParams = PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun build() {
        linkReasonBeingCreated = PersonToCounterPartyLinkToUploadedDocLinkReasonFactories.create.create(wrappedParams)
    }

    private fun validate() {
        PersonToCounterPartyLinkToUploadedDocLinkReasonValidator(linkReasonBeingCreated).createScenario()
        if (!linkReasonBeingCreated.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        linkReasonBeingCreated.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(
                        linkReasonBeingCreated
                )
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

