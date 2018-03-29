package composers.persons.contacts

import models.contact.Contact
import models.contact.ContactRequestParametersWrapper
import models.contact.ContactValidator
import models.contact.updaters.ContactUpdaters
import org.jooq.generated.Tables.CONTACTS
import org.jooq.generated.Tables.PERSON_TO_CONTACT_LINKS
import orm.contactgeneratedrepository.ContactRecord
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class ContactsUpdate(val params: IParam, val personId: Long?, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (Contact)->Unit
    lateinit var onError: (Contact)->Unit

    lateinit var contactBeingUpdated: Contact

    override fun beforeCompose(){
        validateRouteParams()
        findAndSetContactBeingUpdated()
        updateProperties()
        loadContactType()
        validate()
    }

    private fun validateRouteParams() {
        personId ?: failImmediately(BadRequestError())
        id ?: failImmediately(BadRequestError())
    }

    private fun findAndSetContactBeingUpdated() {
        val contact = ContactRecord.GET().preload {
            it.personToContactLink()
        }.join {
            it.personToContactLink()
        }.where(
                CONTACTS.ID.eq(id).and(PERSON_TO_CONTACT_LINKS.PERSON_ID.eq(personId))
        ).limit(1).execute().firstOrNull()

        contact?.let {
            contactBeingUpdated = contact
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun updateProperties() {
        params.get("contact")?.let {
            val wrappedRequestParams = ContactRequestParametersWrapper(it)
            ContactUpdaters.personDefault(contactBeingUpdated, wrappedRequestParams)
        } ?: failImmediately(BadRequestError())
    }

    private fun validate(){
        ContactValidator(contactBeingUpdated).personUpdateScenario()
        if (!contactBeingUpdated.record.validationManager.isValid()) {
            println("invalid")
            failImmediately(ModelInvalidError(""))
        } else {
            println("valid")
        }
    }


    override fun compose(){
        contactBeingUpdated.record.save()
    }

    private fun loadContactType(){
        contactBeingUpdated.record.loadContactType()
    }

    override fun fail(error: Throwable) {
        println("error")
        println(error)
        when(error) {
            is BadRequestError -> {
                Contact().also {
                    it.record.validationManager.addGeneralError("unprocessable entry")
                }
            }
            is ModelNotFoundError -> {
                Contact().also {
                    it.record.validationManager.addGeneralError("such contact was not found")
                }
            }
            is ModelInvalidError -> {
                onError(contactBeingUpdated)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contactBeingUpdated)
    }

}

