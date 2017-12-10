package composers.contacttypes

import composers.sessions.Create
import models.contacttype.ContactType
import org.jooq.generated.Tables.CONTACT_TYPES
import orm.contacttypegeneratedrepository.ContactTypeRecord
import utils.composer.ComposerBase
import utils.requestparameters.IParam

class Destroy(val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (ContactType)->Unit
    lateinit var onError: (ContactType)->Unit

    lateinit var contactType: ContactType

    override fun beforeCompose(){
        id ?: failImmediately(Update.UnprocessableEntry())

        ContactTypeRecord.GET().where(CONTACT_TYPES.ID.eq(id!!)).execute().firstOrNull()?.let {
            contactType = it
        } ?: failImmediately(Create.RecordNotFound())
    }

    override fun compose(){
        contactType.record.delete()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is Update.UnprocessableEntry -> {
                ContactType().let {
                    it.record.validationManager.addError("general", "unprocessable entry")
                    this.onError(it)
                }
            }
            is Create.RecordNotFound -> {
                ContactType().let {
                    it.record.validationManager.addError("general", "no such contact type")
                    this.onError(it)
                }
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contactType)
    }

}

