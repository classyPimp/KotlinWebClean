package models.approvalsteptouploadeddocumentlink

import models.uploadeddocument.UploadedDocument
import models.uploadeddocument.UploadedDocumentValidator
import orm.approvalsteptouploadeddocumentlinkgeneratedrepository.ApprovalStepToUploadedDocumentLinkValidatorTrait

class ApprovalStepToUploadedDocumentLinkValidator(model: ApprovalStepToUploadedDocumentLink) : ApprovalStepToUploadedDocumentLinkValidatorTrait(model, model.record.validationManager) {

    fun ofContractCreateScenario(){
        ofContractValidateUplaodedDocument()
    }

    fun ofContractValidateUplaodedDocument() {
        val uploadedDocument = model.uploadedDocument
        if (uploadedDocument == null) {
            validationManager.addGeneralError("no document selected")
            return
        }
        UploadedDocumentValidator(uploadedDocument).createScenario()
        if (!uploadedDocument.record.validationManager.isValid()) {
            validationManager.markAsHasNestedErrors()
        }
    }

}