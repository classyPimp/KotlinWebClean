package composers.counterparties

import models.counterparty.CounterParty
import org.jooq.generated.Tables.COUNTER_PARTIES
import orm.counterpartygeneratedrepository.CounterPartyRecord
import orm.modelUtils.exceptions.ModelNotFoundError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError

class Destroy(val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (CounterParty)->Unit
    lateinit var onError: (CounterParty)->Unit

    lateinit var counterPartyBeingDestroyed: CounterParty

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError())
        findAndSetCounterPartyBeingDestroyed()
    }

    private fun findAndSetCounterPartyBeingDestroyed(){
        CounterPartyRecord.GET().where(
                COUNTER_PARTIES.ID.eq(id)
        ).execute().firstOrNull()?.let {
            counterPartyBeingDestroyed = it
        } ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        counterPartyBeingDestroyed.record.delete()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        CounterParty().also {
                            it.record.validationManager.addGeneralError("can't delete: no such counter party")
                        }
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(counterPartyBeingDestroyed)
    }

}

