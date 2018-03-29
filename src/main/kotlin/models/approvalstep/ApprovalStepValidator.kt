package models.approvalstep

import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import models.approvalsteptoapproverlink.ApprovalStepToApproverLinkValidator
import models.approvalsteptouploadeddocumentlink.ApprovalStepToUploadedDocumentLinkValidator
import orm.approvalstepgeneratedrepository.ApprovalStepValidatorTrait
import sun.plugin.dom.exception.InvalidStateException

class ApprovalStepValidator(model: ApprovalStep) : ApprovalStepValidatorTrait(model, model.record.validationManager) {

    fun ofContractCreateScenario(){
        validateApprovalStepToApproverLinksOfContract()
        validateApprovalStepToUploadedDocumentLinksOfContract()
    }

    private fun validateApprovalStepToApproverLinksOfContract() {
        val approvalStepToApproverLinks = model.approvalStepToApproverLinks
        if (approvalStepToApproverLinks == null || approvalStepToApproverLinks.isEmpty()) {
            throw InvalidStateException("ApprovalStep: no links to approvers where added")
        }
        approvalStepToApproverLinks.forEach {
            ApprovalStepToApproverLinkValidator(it).ofContractCreateScenario()
            if (!it.record.validationManager.isValid()) {
                validationManager.markAsHasNestedErrors()
            }
        }
    }

    private fun validateApprovalStepToUploadedDocumentLinksOfContract() {
        val approvalStepToUploadedDocumentLinks = model.approvalStepToUploadedDocumentLinks
        if (approvalStepToUploadedDocumentLinks == null || approvalStepToUploadedDocumentLinks.isEmpty()) {
            validationManager.addGeneralError("no files to be approved where selected")
            return
        }
        approvalStepToUploadedDocumentLinks.forEach {
            ApprovalStepToUploadedDocumentLinkValidator(it).ofContractCreateScenario()
            if (!it.record.validationManager.isValid()) {
                validationManager.markAsHasNestedErrors()
            }
        }
    }

}