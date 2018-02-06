package models.contracttocounterpartylink

import orm.contracttocounterpartylinkgeneratedrepository.ContractToCounterPartyLinkValidatorTrait

class ContractToCounterPartyLinkValidator(model: ContractToCounterPartyLink) : ContractToCounterPartyLinkValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

    fun forContractCreateScenario() {
        validateCounterPartyId()
        validateRoleAccordingToContract()
    }

    private fun validateCounterPartyId() {
        val counterParty = model.counterParty
        if (counterParty == null) {
            validationManager.addGeneralError("no such counter party in database")
            return
        }
    }

    private fun validateRoleAccordingToContract() {
        val roleAccordingToContract = model.roleAccordingToContract
        if (roleAccordingToContract == null || roleAccordingToContract.isBlank()) {
            validationManager.addRoleAccordingToContractError("should be provided")
            return
        }
    }

}