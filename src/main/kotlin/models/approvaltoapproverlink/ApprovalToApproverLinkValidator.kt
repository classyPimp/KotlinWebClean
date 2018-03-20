package models.approvaltoapproverlink

import orm.approvaltoapproverlinkgeneratedrepository.ApprovalToApproverLinkValidatorTrait

class ApprovalToApproverLinkValidator(model: ApprovalToApproverLink) : ApprovalToApproverLinkValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}