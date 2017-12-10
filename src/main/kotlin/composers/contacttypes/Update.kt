package composers.contacttypes

import composers.sessions.Create
import models.contacttype.ContactType
import models.contacttype.ContactTypeValidator
import org.jooq.generated.Tables.CONTACT_TYPES
import orm.contacttypegeneratedrepository.ContactTypeRecord
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.requestparameters.IParam

class Update(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (ContactType)->Unit
    lateinit var onError: (ContactType)->Unit

    lateinit var contactType: ContactType
    lateinit var contactTypeParams: IParam

    class UnprocessableEntry : Throwable()
    override fun beforeCompose(){
        if (id == null) {
            failImmediately(UnprocessableEntry())
        }

        params.get("contactType")?.let {
            contactTypeParams = it
        } ?: failImmediately(UnprocessableEntry())


        ContactTypeRecord.GET().where(CONTACT_TYPES.ID.eq(id!!)).execute().firstOrNull()?.let {
            contactType = it
        } ?: failImmediately(Create.RecordNotFound())

        updateFields()

        validate()
    }

    private fun updateFields() {
        contactType.record.let {
            it.name = contactTypeParams.get("name")?.string
        }

        if (!contactType.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException("contact type invalid"))
        }
    }

    private fun validate(){
        ContactTypeValidator(contactType).updateScenario()
    }

    override fun compose(){
        contactType.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is UnprocessableEntry -> {
                ContactType().let {
                    it.record.validationManager.addError("general", "unprocessable entry")
                    onError(it)
                }
            }
            is Create.RecordNotFound -> {
                ContactType().let {
                    it.record.validationManager.addError("general", "no such contact type")
                    onError(it)
                }
            }
            is ModelInvalidException -> {
                onError(contactType)
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

