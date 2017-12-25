package models.contact

import models.contacttype.ContactType
import models.persontocontactlink.PersonToContactLinkValidator
import org.jooq.generated.Tables.CONTACT_TYPES
import orm.contactgeneratedrepository.ContactValidatorTrait
import orm.contacttypegeneratedrepository.ContactTypeRecord

class ContactValidator(model: Contact) : ContactValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateContactType()
        validateValue()

    }

    fun personCreateScenario(){
        createScenario()
        validateContactToPersonLink()
    }

    fun personUpdateScenario() {
        personCreateScenario()
    }

    fun validateContactType(){
        if (model.contactTypeId == null) {
            model.record.validationManager.addContactTypeIdError("invalid contact type")
            return
        }

        var contactType = model.contactType
        if (contactType == null) {
            contactType = ContactTypeRecord.GET().where(CONTACT_TYPES.ID.eq(model.contactTypeId)).execute().firstOrNull()
            if (contactType == null) {
                model.record.validationManager.addContactTypeIdError("no such contact type")
                return
            }
        } else {
            if (contactType.isSpecificForType != null && contactType.isSpecificForType != ContactType.Companion.IsSpecificForTypeAllowedValues.person) {
                model.record.validationManager.addContactTypeIdError("invalid contact type")
                return
            }
        }
    }

    private fun validateValue(){
        val value = model.value
        if (value.isNullOrBlank()) {
            model.record.validationManager.addValueError("should not be empty")
            return
        }
    }

    private fun validateContactToPersonLink(){
        val link = model.personToContactLink
        if (link == null) {
            throw Throwable("no person to contact link on contact")
        }
        PersonToContactLinkValidator(link).createScenario()
        if (!link.record.validationManager.isValid()) {
            model.record.validationManager.addGeneralError("link is invalid")
        }
    }


}