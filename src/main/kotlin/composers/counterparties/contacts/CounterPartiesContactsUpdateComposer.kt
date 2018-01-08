package composers.counterparties.contacts

import models.contact.Contact
import models.contact.ContactRequestParametersWrapper
import models.contact.ContactValidator
import models.contact.daos.ContactDaos
import models.contact.updaters.ContactUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class CounterPartiesContactsUpdateComposer(val params: IParam, val counterPartyId: Long?, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (Contact)->Unit
    lateinit var onError: (Contact)->Unit

    lateinit var contactBeingUpdate: Contact
    lateinit var wrappedParams: ContactRequestParametersWrapper

    override fun beforeCompose(){
        counterPartyId ?: failImmediately(UnprocessableEntryError())
        id ?: failImmediately(UnprocessableEntryError())
        wrapParams()
        findAndSetContactBeingUpdate()
        runUpdater()
        preloadAssociatedRequiredForValidation()
        validate()
    }

    private fun wrapParams() {
        params.get("contact")?.let {
            wrappedParams = ContactRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    private fun findAndSetContactBeingUpdate() {
        ContactDaos.update.getForCounterParty(counterPartyId!!, id!!)?.let {
            contactBeingUpdate = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun runUpdater() {
        ContactUpdaters.forCounterPartyDefault(contactBeingUpdate, wrappedParams)
    }

    private fun preloadAssociatedRequiredForValidation() {
        contactBeingUpdate.record.let {
            it.loadContactType()
            it.loadCounterPartyToContactLink()
        }
    }

    private fun validate() {
        ContactValidator(contactBeingUpdate).forCounterPartyUpdateScenario()
        if (!contactBeingUpdate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        contactBeingUpdate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        Contact().also {
                            it.record.validationManager.addGeneralError("no such contact on counter party")
                        }
                )
            }
            is ModelInvalidException -> {
                onError(
                        contactBeingUpdate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contactBeingUpdate)
    }

}

