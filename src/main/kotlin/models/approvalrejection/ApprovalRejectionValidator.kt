package models.approvalrejection

import orm.approvalrejectiongeneratedrepository.ApprovalRejectionValidatorTrait

class ApprovalRejectionValidator(model: ApprovalRejection) : ApprovalRejectionValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}