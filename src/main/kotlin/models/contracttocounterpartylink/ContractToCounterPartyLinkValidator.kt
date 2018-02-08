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

    fun counterPartyReplaceScenario() {
        validateCounterPartyId()
    }

    fun forContractDestroyScenario() {
        validateContract()
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

    private fun validateContract() {
        val contract = model.contract
        if (contract == null) {
            throw IllegalStateException("no contract preloaded for validation")
        }
        val links = contract.contractToCounterPartyLinks
        if (links == null) {
            throw IllegalStateException("no links to counter parties loaded")
        }
        if (links.size < 2) {
            validationManager.addGeneralError("can't be removed, at least one counter party should be set. Try replacing instead")
            return
        }
    }

}