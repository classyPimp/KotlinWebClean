package models.persontocontactlink

import org.jooq.generated.Tables.PEOPLE
import orm.persongeneratedrepository.PersonRecord
import orm.persontocontactlinkgeneratedrepository.PersonToContactLinkValidatorTrait

class PersonToContactLinkValidator(model: PersonToContactLink) : PersonToContactLinkValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validatePersonId()
    }

    private fun validatePersonId(){
        val personId = model.personId

        if (personId == null) {
            model.record.validationManager.addGeneralError("no person id provided")
            return
        }

        val person = PersonRecord.GET().where(PEOPLE.ID.eq(personId)).limit(1).execute().firstOrNull()
        if (person == null) {
            model.record.validationManager.addGeneralError("non existant person")
            return
        }
    }

}