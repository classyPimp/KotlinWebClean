package models.persontocounterpartylinkreason

import orm.persontocounterpartylinkreasongeneratedrepository.PersonToCounterPartyLinkReasonValidatorTrait

class PersonToCounterPartyLinkReasonValidator(model: PersonToCounterPartyLinkReason) : PersonToCounterPartyLinkReasonValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateReasonName()
    }

    private fun validateReasonName() {
        if (model.reasonName.isNullOrBlank()) {
            model.record.validationManager.addReasonNameError("cannot be empty")
            return
        }
    }

    fun updateScenario() {
        createScenario()
    }

}