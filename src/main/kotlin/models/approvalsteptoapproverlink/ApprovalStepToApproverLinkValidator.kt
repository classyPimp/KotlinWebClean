package models.approvalsteptoapproverlink

import orm.approvalsteptoapproverlinkgeneratedrepository.ApprovalStepToApproverLinkValidatorTrait

class ApprovalStepToApproverLinkValidator(model: ApprovalStepToApproverLink) : ApprovalStepToApproverLinkValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}