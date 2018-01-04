package models.persontocounterpartylink

import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkValidatorTrait

class PersonToCounterPartyLinkValidator(model: PersonToCounterPartyLink) : PersonToCounterPartyLinkValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateCounterPartyId()
        validatePersonId()
        validatePersonToCounterPartyLinkReasonId()
        validateSpecificDetails()
    }

    fun updateScenario() {
        model.record.propertiesChangeTracker.let {
            if (it.specificDetailsIsChanged) {
                validateSpecificDetails()
            }
            if (it.personToCounterPartyLinkReasonIdIsChanged) {
                validatePersonToCounterPartyLinkReasonId()
            }
        }
    }

    private fun validateCounterPartyId() {
        val id = model.counterPartyId
        if (id == null) {
            validationManager.addCounterPartyIdError("is not assigned")
            return
        }
        if (model.counterParty == null) {
            validationManager.addGeneralError("no such counter party exists")
            return
        }
    }

    private fun validatePersonId() {
        val id = model.personId
        if (id == null) {
            validationManager.addPersonIdError("is not assigned")
            return
        }
        if (model.person == null) {
            validationManager.addPersonIdError("no such person exists")
            return
        }
    }

    private fun validatePersonToCounterPartyLinkReasonId() {
        val id = model.personToCounterPartyLinkReasonId
        if (id == null) {
            validationManager.addPersonToCounterPartyLinkReasonIdError("is not assigned")
            return
        }
        if (model.personToCounterPartyLinkReasonId == null) {
            validationManager.addPersonToCounterPartyLinkReasonIdError("no such reason exists")
            return
        }
    }

    private fun validateSpecificDetails() {
        model.specificDetails?.let {
            if (it.length > 512) {
                validationManager.addSpecificDetailsError("too long, should be less than 512 chars")
            }
        }
    }

}