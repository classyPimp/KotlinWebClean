package models.approval

import models.approvalstep.ApprovalStepValidator
import models.approvaltoapproverlink.ApprovalToApproverLink
import models.approvaltoapproverlink.ApprovalToApproverLinkValidator
import models.contract.daos.ContractDaos
import org.omg.CORBA.DynAnyPackage.Invalid
import orm.approvalgeneratedrepository.ApprovalValidatorTrait
import orm.contractgeneratedrepository.ContractRecord

class ApprovalValidator(model: Approval) : ApprovalValidatorTrait(model, model.record.validationManager) {

    fun ofContractCreateScenario(){
        ofContractValidateApprovableId()
        ofContractValidateApprovableType()
        ofContractValidateApprovalToapproverLinks()
        ofContractValidateApprovalSteps()
        validateSizeEqualityOfApproversAndApproversInStepSize()
    }

    private fun ofContractValidateApprovableId() {
        val approvableId = model.approvableId
        if (approvableId == null) {
            throw IllegalStateException("no approvableId supplied")
        }
        if (!ContractDaos.show.exists(approvableId)) {
            validationManager.addGeneralError("contract does not exist")
            return
        }
    }

    private fun ofContractValidateApprovableType() {
        val approvableType = model.approvableType
        if (approvableType.isNullOrBlank() || approvableType != "Contract") {
            throw IllegalStateException("approvble type was not set or is incorrect")
        }
    }

    private fun ofContractValidateApprovalToapproverLinks() {
        val approvalToApproverLinks = model.approvalToApproverLinks
        if (approvalToApproverLinks == null || approvalToApproverLinks.isEmpty()) {
            validationManager.addGeneralError("approvers should be added")
            return
        }
        if (approvalToApproverLinks.size < 2) {
            validationManager.addGeneralError("at least two approvers should be assigned")
        }
        approvalToApproverLinks.forEach {
            ApprovalToApproverLinkValidator(it).ofContractCreateScenario()
            if (!it.record.validationManager.isValid()) {
                validationManager.markAsHasNestedErrors()
            }
        }
    }

    private fun ofContractValidateApprovalSteps() {
        val approvalSteps = model.approvalSteps
        if (approvalSteps == null || approvalSteps.isEmpty() || approvalSteps.size > 1) {
            throw IllegalStateException("no approval step was added")
        }
        approvalSteps.forEach {
            ApprovalStepValidator(it).ofContractCreateScenario()
            if (!it.record.validationManager.isValid()) {
                validationManager.markAsHasNestedErrors()
            }
        }
    }

    private fun validateSizeEqualityOfApproversAndApproversInStepSize() {
        val approvalToApproverLinks = model.approvalToApproverLinks
        val approvalStepToApproverLinks = model.approvalSteps?.getOrNull(0)?.approvalStepToApproverLinks
        if (approvalStepToApproverLinks == null || approvalToApproverLinks == null) {
            return
        }
        if (approvalStepToApproverLinks.size != approvalStepToApproverLinks.size) {
            throw IllegalStateException("no approver links duplicated on ApprovalStep")
        }
    }

}