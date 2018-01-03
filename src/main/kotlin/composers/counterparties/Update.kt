package composers.counterparties

import models.counterparty.CounterParty
import models.counterparty.CounterPartyRequestParametersWrapper
import models.counterparty.CounterPartyValidator
import models.counterparty.daos.CounterPartyDaos
import models.counterparty.updaters.CounterPartyUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class Update(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (CounterParty)->Unit
    lateinit var onError: (CounterParty)->Unit

    lateinit var counterPartyBeingUpdated: CounterParty
    lateinit var wrappedParams: CounterPartyRequestParametersWrapper

    override fun beforeCompose(){
        id ?: failImmediately(UnprocessableEntryError())
        wrapParams()
        findAndSetCounterPartyBeingUpdated()
        runUpdater()
        validate()
    }

    private fun wrapParams(){
        params.get("counterParty")?.let {
            wrappedParams = CounterPartyRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    private fun findAndSetCounterPartyBeingUpdated() {
        CounterPartyDaos.edit.getById(id!!)?.let {
            counterPartyBeingUpdated = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun runUpdater() {
        CounterPartyUpdaters.defaultUpdater.run(counterPartyBeingUpdated, wrappedParams)
    }

    private fun validate() {
        CounterPartyValidator(counterPartyBeingUpdated).updateScenario()
        if (!counterPartyBeingUpdated.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        counterPartyBeingUpdated.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        CounterParty().also {
                            it.record.validationManager.addGeneralError("no such counter party")
                        }
                )
            }
            is ModelInvalidException -> {
                onError(
                        counterPartyBeingUpdated
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(counterPartyBeingUpdated)
    }

}

