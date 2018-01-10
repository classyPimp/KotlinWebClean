package models.persontocounterpartylinktouploadeddoclinkreason

import orm.persontocounterpartylinktouploadeddoclinkreasongeneratedrepository.PersonToCounterPartyLinkToUploadedDocLinkReasonValidatorTrait

class PersonToCounterPartyLinkToUploadedDocLinkReasonValidator(model: PersonToCounterPartyLinkToUploadedDocLinkReason) : PersonToCounterPartyLinkToUploadedDocLinkReasonValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateReasonName()
    }

    fun updateScenario() {
        createScenario()
    }

    fun validateReasonName() {
        if (model.reasonName.isNullOrBlank()) {
            validationManager.addReasonNameError("reason name not provided")
            return
        }
        model.reasonName?.let {
            if (it.length < 2) {
                validationManager.addReasonNameError("reason name is too short")
            }
        }
    }

}