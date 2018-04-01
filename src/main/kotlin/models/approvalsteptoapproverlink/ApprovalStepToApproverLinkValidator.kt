package models.approvalsteptoapproverlink

import models.user.daos.UserDaos
import orm.approvalsteptoapproverlinkgeneratedrepository.ApprovalStepToApproverLinkValidatorTrait


class ApprovalStepToApproverLinkValidator(model: ApprovalStepToApproverLink) : ApprovalStepToApproverLinkValidatorTrait(model, model.record.validationManager) {

    fun ofContractCreateScenario(){
        validateUserId()
    }

    private fun validateUserId() {
        val userId = model.userId
        userId ?:  throw IllegalStateException("ApprovalStep ApprovalStepToApproverLink: no userId assigned")
    }

}