package models.approvalstep

import models.approval.Approval
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import models.approvalsteptoapproverlink.ApprovalStepToApproverLinkValidator
import models.approvalsteptouploadeddocumentlink.ApprovalStepToUploadedDocumentLinkValidator
import orm.approvalstepgeneratedrepository.ApprovalStepValidatorTrait

class ApprovalStepValidator(model: ApprovalStep) : ApprovalStepValidatorTrait(model, model.record.validationManager) {

    fun ofContractCreateScenario(){
        validateApprovalStepToApproverLinksOfContract()
        validateApprovalStepToUploadedDocumentLinksOfContract()
    }

    fun ofContractCreateAfterFirstStep(approvalStepToCreate: ApprovalStep, approval: Approval) {
        validateApprovalStepToApproverLinksOfContract()
        validateApprovalStepToUploadedDocumentLinksOfContract()
    }

    private fun validateApprovalStepToApproverLinksOfContract() {
        val approvalStepToApproverLinks = model.approvalStepToApproverLinks
        if (approvalStepToApproverLinks == null || approvalStepToApproverLinks.isEmpty()) {
            throw IllegalStateException("ApprovalStep: no links to approvers where added")
        }
        if (approvalStepToApproverLinks.size < 2) {
            validationManager.addGeneralError("at least 1 approver needed")
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