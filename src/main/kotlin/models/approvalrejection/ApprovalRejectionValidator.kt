package models.approvalrejection

import models.approvalrejectiontouploadeddocumentlink.ApprovalRejectionToUploadedDocumentLinkValidator
import models.uploadeddocument.UploadedDocumentValidator
import orm.approvalrejectiongeneratedrepository.ApprovalRejectionValidatorTrait

class ApprovalRejectionValidator(model: ApprovalRejection) : ApprovalRejectionValidatorTrait(model, model.record.validationManager) {

    fun ofContractCreateScenario(){
        validateReasonText()
        validateApprovalRejectionToUploadedDocumentLinks()
    }

    fun validateReasonText() {
        val reasonText = model.reasonText
        if (reasonText == null || reasonText.isBlank()) {
            validationManager.addReasonTextError("should be provided")
        }
    }

    fun validateApprovalRejectionToUploadedDocumentLinks() {
        val approvalRejectionToUploadedDocumentLinks = model.approvalRejectionToUploadedDocumentLinks
        if (approvalRejectionToUploadedDocumentLinks == null || approvalRejectionToUploadedDocumentLinks.isEmpty()) {
            return
        }
        approvalRejectionToUploadedDocumentLinks.forEach {
            ApprovalRejectionToUploadedDocumentLinkValidator(it).ofContractCreateScenario()
            it.uploadedDocument?.let {
                UploadedDocumentValidator(it).createScenario()
                if (!it.record.validationManager.isValid()) {
                    validationManager.markAsHasNestedErrors()
                }
            } ?: throw IllegalStateException()
            if (!it.record.validationManager.isValid()) {
                validationManager.markAsHasNestedErrors()
            }
        }
    }

}