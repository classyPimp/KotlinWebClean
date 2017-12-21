package composers.persons.contacts

import models.contact.Contact
import models.contact.ContactRequestParametersWrapper
import models.contact.ContactValidator
import models.contact.factories.ContactFactories
import models.person.Person
import org.jooq.generated.Tables.PEOPLE
import orm.persongeneratedrepository.PersonRecord
import orm.services.ModelInvalidException
import orm.utils.TransactionRunner
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class ContactsCreate(val params: IParam, val personId: Long?) : ComposerBase() {

    lateinit var onSuccess: (Contact)->Unit
    lateinit var onError: (Contact)->Unit

    var person: Person? = null
    lateinit var contactBeingCreated: Contact
    lateinit var contactParams: ContactRequestParametersWrapper

    override fun beforeCompose(){
        personId ?: failImmediately(UnprocessableEntryError("no person id route param provided"))

        wrapContactParams()

        findAndSetPerson()

        buildContact()

        validate()

    }

    fun wrapContactParams(){
        params.get("contact")?.let {
            contactParams = ContactRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    class PersonNotFound : Throwable()
    private fun findAndSetPerson() {
        PersonRecord.GET().where(PEOPLE.ID.eq(personId!!)).execute().firstOrNull()?.let {
            person = it
        } ?: failImmediately(PersonNotFound())
    }

    private fun buildContact() {
        contactBeingCreated = ContactFactories.personCreate(contactParams, personId!!)
    }

    private fun validate() {
        contactBeingCreated.let {
            println(it.contactType)
            println(it.contactTypeId)
            println(it.value)
        }
        ContactValidator(contactBeingCreated).personCreateScenario()
    }

    override fun compose(){
        TransactionRunner.run { tx ->
            contactBeingCreated.record.saveCascade(tx.inTransactionDsl)
        }
        preloadRequired()
    }

    private fun preloadRequired(){
        contactBeingCreated.record.loadContactType()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is PersonNotFound -> {
                Contact().let {
                    it.record.validationManager.addGeneralError("no person found")
                    onError(it)
                }
            }
            is UnprocessableEntryError -> {
                Contact().let {
                    it.record.validationManager.addGeneralError("unprocessable entry")
                    onError(it)
                }
            }
            is ModelInvalidException -> {
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

