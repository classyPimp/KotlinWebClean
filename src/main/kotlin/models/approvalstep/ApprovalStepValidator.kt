package models.approvalstep

import orm.approvalstepgeneratedrepository.ApprovalStepValidatorTrait

class ApprovalStepValidator(model: ApprovalStep) : ApprovalStepValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}