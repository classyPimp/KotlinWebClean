package models.approvalsteptoapproverlink

import models.user.daos.UserDaos
import orm.approvalsteptoapproverlinkgeneratedrepository.ApprovalStepToApproverLinkValidatorTrait
import sun.plugin.dom.exception.InvalidStateException

class ApprovalStepToApproverLinkValidator(model: ApprovalStepToApproverLink) : ApprovalStepToApproverLinkValidatorTrait(model, model.record.validationManager) {

    fun ofContractCreateScenario(){
        validateUserId()
    }

    private fun validateUserId() {
        val userId = model.userId
        userId ?:  throw InvalidStateException("ApprovalStep ApprovalStepToApproverLink: no userId assigned")
    }

}