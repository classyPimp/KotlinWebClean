package composers.persontocounterpartylinktouploadeddoclinkreasons

import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReason
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReasonRequestParametersWrapper
import models.persontocounterpartylinktouploadeddoclinkreason.PersonToCounterPartyLinkToUploadedDocLinkReasonValidator
import models.persontocounterpartylinktouploadeddoclinkreason.factories.PersonToCounterPartyLinkToUploadedDocLinkReasonFactories
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
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
        } ?: failImmediately(UnprocessableEntryError())
    }

    private fun build() {
        linkReasonBeingCreated = PersonToCounterPartyLinkToUploadedDocLinkReasonFactories.create.create(wrappedParams)
    }

    private fun validate() {
        PersonToCounterPartyLinkToUploadedDocLinkReasonValidator(linkReasonBeingCreated).createScenario()
        if (!linkReasonBeingCreated.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        linkReasonBeingCreated.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidException -> {
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

