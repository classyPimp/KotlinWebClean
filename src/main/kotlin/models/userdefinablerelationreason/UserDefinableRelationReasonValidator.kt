package models.userdefinablerelationreason

import orm.userdefinablerelationreasongeneratedrepository.UserDefinableRelationReasonValidatorTrait

class UserDefinableRelationReasonValidator(model: UserDefinableRelationReason) : UserDefinableRelationReasonValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}