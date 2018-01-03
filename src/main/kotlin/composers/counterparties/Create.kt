package composers.counterparties

import models.counterparty.CounterParty
import models.counterparty.CounterPartyRequestParametersWrapper
import models.counterparty.CounterPartyValidator
import models.counterparty.factories.CounterPartyFactories
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class Create(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (CounterParty)->Unit
    lateinit var onError: (CounterParty)->Unit

    lateinit var wrappedParams: CounterPartyRequestParametersWrapper
    lateinit var counterPartyBeingCreated: CounterParty

    override fun beforeCompose(){
        wrapParams()
        build()
        validate()
    }

    fun wrapParams(){
        params.get("counterParty")?.let {
            wrappedParams = CounterPartyRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    fun build(){
        counterPartyBeingCreated = CounterPartyFactories.defaultCreate.create(wrappedParams)
    }

    fun validate(){
        CounterPartyValidator(counterPartyBeingCreated).createScenario()
        if (!counterPartyBeingCreated.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        counterPartyBeingCreated.record.save()
        afterCompose()
    }

    private fun afterCompose(){
        preloadIncorporationForm()
    }

    private fun preloadIncorporationForm(){
        counterPartyBeingCreated.record.loadIncorporationForm()
    }


    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidException -> {
                onError(
                        counterPartyBeingCreated
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(counterPartyBeingCreated)
    }

}

