package composers.persons.contacts

import models.contact.Contact
import org.jooq.generated.Tables.CONTACTS
import org.jooq.generated.Tables.PERSON_TO_CONTACT_LINKS
import orm.contactgeneratedrepository.ContactRecord
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.utils.TransactionRunner
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class ContactsDestroy(val personId: Long?, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (Contact)->Unit
    lateinit var onError: (Contact)->Unit

    var contactBeingDeleted: Contact? = null

    override fun beforeCompose(){
        checkRouteParams()
        findAndSetContactBeingdeleted()
    }

    private fun checkRouteParams(){
        personId ?: failImmediately(UnprocessableEntryError())
        id ?: failImmediately(UnprocessableEntryError())
    }

    private fun findAndSetContactBeingdeleted(){
        contactBeingDeleted = ContactRecord.GET().join {
            it.personToContactLink()
        }.preload {
            it.personToContactLink()
        }.where(
                CONTACTS.ID.eq(id).and(PERSON_TO_CONTACT_LINKS.PERSON_ID.eq(personId))
        ).limit(1).execute().firstOrNull()
        contactBeingDeleted ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        TransactionRunner.run {
            contactBeingDeleted!!.record.delete(it.inTransactionDsl)
        }
    }

    override fun fail(error: Throwable) {
        when(error) {
            is UnprocessableEntryError -> {
                onError(
                        Contact().also {
                            it.record.validationManager.addGeneralError("unprocessable entry")
                        }
                )
            }
            is ModelNotFoundError -> {
                onError(
                        Contact().also {
                            it.record.validationManager.addGeneralError("no such contact on person")
                        }
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contactBeingDeleted!!)
    }

}

