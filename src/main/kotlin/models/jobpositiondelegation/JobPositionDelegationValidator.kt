package models.jobpositiondelegation

import orm.jobpositiondelegationgeneratedrepository.JobPositionDelegationValidatorTrait

class JobPositionDelegationValidator(model: JobPositionDelegation) : JobPositionDelegationValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}