package models.contract

import models.contractstatus.ContractStatusValidator
import models.contracttocounterpartylink.ContractToCounterPartyLink
import models.contracttocounterpartylink.ContractToCounterPartyLinkValidator
import orm.contractgeneratedrepository.ContractValidatorTrait
import sun.plugin.dom.exception.InvalidStateException

class ContractValidator(model: Contract) : ContractValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateDescription()
        validateContractToCounterPartyLinks()
        validateContractStatus()
        validateCategory()
    }

    fun manageUpdateScenario() {
        if (model.record.propertiesChangeTracker.contractCategoryIdIsChanged) {
            validateCategory()
        }
        if (model.record.propertiesChangeTracker.descriptionIsChanged) {
            validateDescription()
        }
    }

    fun updateGeneralInfoScenario() {
        model.record.let {record ->
            if (record.propertiesChangeTracker.descriptionIsChanged) {
                validateDescription()
            }
            if (record.propertiesChangeTracker.contractCategoryIdIsChanged) {
                validateCategory()
            }
        }
    }

    private fun validateDescription() {
        val description = model.description
        if (description == null || description.isBlank()) {
            validationManager.addDescriptionError("should be provided")
            return
        }
        if (description.length < 3) {
            validationManager.addDescriptionError("too short, should be at least 3 characters long")
            return
        }
    }

    private fun validateContractToCounterPartyLinks() {
        val links = model.contractToCounterPartyLinks

        if (links == null || links.isEmpty()) {
            validationManager.addGeneralError("select at least one counter party")
            return
        }

        val ids = links.mapTo(mutableListOf<Long?>()) {
            it.id
        }
        ids.let {
            if (it.size != it.toSet().size) {
                validationManager.addGeneralError("same counter party was added more than once")
            }
        }


        var hasNestedErrors = false
        links.forEach {
            ContractToCounterPartyLinkValidator(it).forContractCreateScenario()
            if (!it.record.validationManager.isValid()) {
                hasNestedErrors = true
            }
        }
        if (hasNestedErrors) {
            validationManager.addGeneralError("counter parties invalid")
        }

    }

    private fun validateContractStatus() {
        val contractStatus = model.contractStatus
        if (contractStatus == null) {
            throw InvalidStateException("no contract status initialized")
        }
        ContractStatusValidator(contractStatus).forContractCreateScenario()
    }

    private fun validateCategory() {
        val contractCategory = model.contractCategory
        if (contractCategory == null || contractCategory.id == null) {
            validationManager.addContractCategoryError("should be selected")
            return
        }
    }

}