package models.contacttype

import org.jooq.generated.Tables.CONTACT_TYPES
import orm.contacttypegeneratedrepository.ContactTypeRecord
import orm.contacttypegeneratedrepository.ContactTypeValidatorTrait

class ContactTypeValidator(model: ContactType) : ContactTypeValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateName()
        model.name?.let {
            validateNameUniqness()
        }
    }

    fun updateScenario() {
        createScenario()
    }

    private fun validateName() {
        nameTester().let {
            test ->
            if (!test.shouldNotBeNull(model.name)) {
                return
            }

            if (!test.shouldBeLongerThan(model.name!!, 0)) {
                return
            }
        }

    }

    private fun validateNameUniqness(){
        ContactTypeRecord.GET().where(CONTACT_TYPES.NAME.eq(model.name!!)).execute().firstOrNull()?.let {
            model.record.validationManager.addNameError("such name already exists")
        }
    }

}