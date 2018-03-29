package composers.counterparties.contacts

import models.contact.Contact
import models.contact.daos.ContactDaos
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.utils.TransactionRunner
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError

class CounterPartiesContactsDestroyComposer(val counterPartyId: Long?, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (Contact)->Unit
    lateinit var onError: (Contact)->Unit

    lateinit var contactBeingDestroyed: Contact

    override fun beforeCompose(){
        counterPartyId ?: failImmediately(BadRequestError())
        id ?: failImmediately(BadRequestError())
        findAndSetContactBeingDestroyed()
    }

    private fun findAndSetContactBeingDestroyed() {
        ContactDaos.destroy.forCounterParty(counterPartyId!!, id!!)?.let {
            contactBeingDestroyed = it
        } ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        TransactionRunner.run {
            contactBeingDestroyed.record.delete(it.inTransactionDsl)
        }
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        Contact().also {
                            it.record.validationManager.addGeneralError("no such contact")
                        }
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contactBeingDestroyed)
    }

}

