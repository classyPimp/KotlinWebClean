package composers.counterparties.contacts

import models.contact.Contact
import models.contact.ContactRequestParametersWrapper
import models.contact.ContactValidator
import models.contact.factories.ContactFactories
import models.counterpartytocontactlink.factories.CounterPartyToContactLinkFactories
import orm.services.ModelInvalidError
import orm.utils.TransactionRunner
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class CounterPartiesContactsCreateComposer(val params: IParam, val counterPartyId: Long?) : ComposerBase() {

    lateinit var onSuccess: (Contact)->Unit
    lateinit var onError: (Contact)->Unit

    lateinit var contactBeingCreated: Contact
    lateinit var contactWrappedParams: ContactRequestParametersWrapper

    override fun beforeCompose(){
        counterPartyId ?: failImmediately(BadRequestError())
        wrapParams()
        buildContact()
        buildCounterPartyToContactLink()
        preloadContactTypeForValidation()
        validate()
    }

    private fun wrapParams(){
        params.get("contact")?.let {
            contactWrappedParams = ContactRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun buildContact() {
        contactBeingCreated = ContactFactories.forCounterPartyCreate(contactWrappedParams)
    }

    private fun buildCounterPartyToContactLink() {
        val link = CounterPartyToContactLinkFactories.ForContacts.create.create(counterPartyId!!)
        contactBeingCreated.counterPartyToContactLink = link
    }

    private fun preloadContactTypeForValidation(){
        contactBeingCreated.record.loadContactType()
    }

    private fun validate() {
        ContactValidator(contactBeingCreated).forCounterPartyCreateScenario()
        if (!contactBeingCreated.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        TransactionRunner.run {
            contactBeingCreated.record.saveCascade(it.inTransactionDsl)
        }
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(contactBeingCreated)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contactBeingCreated)
    }

}

