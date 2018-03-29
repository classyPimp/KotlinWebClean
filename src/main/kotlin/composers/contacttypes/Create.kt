package composers.contacttypes

import models.contacttype.ContactType
import models.contacttype.ContactTypeValidator
import models.contacttype.factories.ContactTypeFactories
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.requestparameters.IParam

class Create(val requestParams: IParam) : ComposerBase() {

    lateinit var onSuccess: (ContactType)->Unit
    lateinit var onError: (ContactType)->Unit

    lateinit var contactType: ContactType
    lateinit var contactTypeParams: IParam

    override fun beforeCompose(){
        requestParams.get("contactType")?.let {
            contactTypeParams = it
        } ?: failInvalidParams()

        constructContactType()
        validate()
    }

    class InvalidParameters: Throwable("")

    private fun failInvalidParams() {
        failImmediately(InvalidParameters())
    }

    private fun constructContactType() {
        contactType = ContactTypeFactories.create(contactTypeParams)
    }

    private fun validate(){
        ContactTypeValidator(contactType).createScenario()
    }

    override fun compose(){
        contactType.record.save()
    }

    override fun fail(error: Throwable) {

        when(error) {
            is InvalidParameters -> {
                val erronousContactType = ContactType()
                erronousContactType.record.validationManager.addError("general", "unprocessable entry")
                this.onError(erronousContactType)
            }
            is ModelInvalidError -> {
                this.onError(contactType)
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

