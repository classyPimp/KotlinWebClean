package models.approvalrejectiontouploadeddocumentlink

import orm.approvalrejectiontouploadeddocumentlinkgeneratedrepository.ApprovalRejectionToUploadedDocumentLinkValidatorTrait

class ApprovalRejectionToUploadedDocumentLinkValidator(model: ApprovalRejectionToUploadedDocumentLink) : ApprovalRejectionToUploadedDocumentLinkValidatorTrait(model, model.record.validationManager) {

    fun ofContractCreateScenario(){
        validateDescription()
    }

    private fun validateDescription() {
        val description = model.description
        if (description == null || description.isBlank()) {
            validationManager.addDescriptionError("should be provided")
            return
        }
    }

}