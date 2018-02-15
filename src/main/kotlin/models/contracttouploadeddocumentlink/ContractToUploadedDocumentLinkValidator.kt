package models.contracttouploadeddocumentlink

import orm.contracttouploadeddocumentlinkgeneratedrepository.ContractToUploadedDocumentLinkValidatorTrait

class ContractToUploadedDocumentLinkValidator(model: ContractToUploadedDocumentLink) : ContractToUploadedDocumentLinkValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateContract()
        validateContractToUploadedDocumentLinkReason()
        validateUploadedDocument()
    }

    fun updateScenario() {
        validateDescription()
    }

    private fun validateContract() {
        if (model.contractId == null) {
            throw IllegalStateException()
        }
        val contract = model.contract
        if (contract == null) {
            validationManager.addGeneralError("no such contract in database")
            return
        }
    }

    private fun validateUploadedDocument() {
        val uploadedDocument = model.uploadedDocument
        if (uploadedDocument == null) {
            validationManager.addGeneralError("file invalid")
        }
    }

    private fun validateContractToUploadedDocumentLinkReason() {
        if (model.contractToUploadedDocumentLinkReasonId == null) {
            validationManager.addContractToUploadedDocumentLinkReasonIdError("should be assigned")
            return
        }
        val contractToUploadedDocumentlinkReason = model.contractToUploadedDocumentLinkReason
        if (contractToUploadedDocumentlinkReason == null) {
            validationManager.addContractToUploadedDocumentLinkReasonIdError("no such reason in database")
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