package models.contractstatus

import orm.contractstatusgeneratedrepository.ContractStatusValidatorTrait

class ContractStatusValidator(model: ContractStatus) : ContractStatusValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

    fun forContractUpdateScenario() {

    }

    fun forContractCreateScenario() {
        validateIsProjectShouldBeTrue()
    }

    private fun validateIsProjectShouldBeTrue() {
        val isProject = model.isProject

        if (isProject == null || isProject != true) {
            throw IllegalStateException("contract status should be true got ${isProject} instead")
        }
    }
}