package models.approval

import orm.approvalgeneratedrepository.ApprovalValidatorTrait

class ApprovalValidator(model: Approval) : ApprovalValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}