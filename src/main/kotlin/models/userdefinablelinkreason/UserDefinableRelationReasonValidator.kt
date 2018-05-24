package models.userdefinablelinkreason

import orm.userdefinablelinkreasongeneratedrepository.UserDefinableLinkReasonValidatorTrait

class UserDefinableRelationReasonValidator(model: UserDefinableLinkReason) : UserDefinableLinkReasonValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}