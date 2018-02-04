package models.contracttouploadeddocumentlinkreason

import orm.contracttouploadeddocumentlinkreasongeneratedrepository.ContractToUploadedDocumentLinkReasonValidatorTrait

class ContractToUploadedDocumentLinkReasonValidator(model: ContractToUploadedDocumentLinkReason) : ContractToUploadedDocumentLinkReasonValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateName()
        validateDescription()
    }

    fun updateScenario() {
        createScenario()
    }

    private fun validateName() {
        val name = model.name

        if (name == null || name.isBlank()) {
            validationManager.addNameError("should be provided")
            return
        }
    }

    private fun validateDescription() {
        val description = model.description

        if (description == null || description.isBlank()) {
            validationManager.addDescriptionError("should be provided")
            return
        }

    }
}