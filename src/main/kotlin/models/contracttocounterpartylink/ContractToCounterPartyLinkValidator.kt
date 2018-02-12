package models.contracttocounterpartylink

import models.contract.Contract
import orm.contracttocounterpartylinkgeneratedrepository.ContractToCounterPartyLinkValidatorTrait

class ContractToCounterPartyLinkValidator(model: ContractToCounterPartyLink) : ContractToCounterPartyLinkValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateContractWhenCreate()
        validateCounterPartyId()
    }

    fun updateScenario() {
        validateRoleAccordingToContract()
    }

    fun forContractCreateScenario() {
        validateCounterPartyId()
        validateRoleAccordingToContract()
    }

    fun counterPartyReplaceScenario() {
        validateCounterPartyId()
        validateUniqunessOfCounterPartiesOnContrat()
    }

    fun forContractDestroyScenario() {
        validateContractWhenDestroy()
    }

    private fun validateCounterPartyId() {
        val counterParty = model.counterParty
        if (counterParty == null) {
            validationManager.addGeneralError("no such counter party in database")
            return
        }
        if (model.counterPartyId == null) {
            throw IllegalStateException()
        }
    }

    private fun validateRoleAccordingToContract() {
        val roleAccordingToContract = model.roleAccordingToContract
        if (roleAccordingToContract == null || roleAccordingToContract.isBlank()) {
            validationManager.addRoleAccordingToContractError("should be provided")
            return
        }
    }

    private fun validateContractWhenDestroy() {
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

    private fun validateContractWhenCreate() {
        val contract = model.contract
        if (model.contractId == null) {
            throw IllegalStateException("no contracId provided")
        }
        if (contract == null) {
            validationManager.addGeneralError("no such contract")
            return
        }
        val existingCounterPartyIdsOnContract = contract.contractToCounterPartyLinks!!.map {
            it.counterPartyId
        }

        if (existingCounterPartyIdsOnContract.contains(model.counterPartyId)) {
            validationManager.addGeneralError("already contains such counter party")
        }

    }

    private fun validateUniqunessOfCounterPartiesOnContrat() {
        val contract = model.contract!!
        val existingCounterPartyIdsOnContract = contract.contractToCounterPartyLinks!!.map {
            it.counterPartyId
        }
        if (existingCounterPartyIdsOnContract.contains(model.counterPartyId)) {
            validationManager.addGeneralError("already contains such counter party")
        }
    }

}