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
        validateInternalNumber()
    }

    private fun validateIsProjectShouldBeTrue() {
        val isProject = model.isProject

        if (isProject == null || isProject != true) {
            throw IllegalStateException("contract status should be true got ${isProject} instead")
        }
    }

    private fun validateInternalNumber() {
        val internalNumber = model.internalNumber

        if (internalNumber == null) {
            throw IllegalStateException("no internal number assigned")
        }
    }

}