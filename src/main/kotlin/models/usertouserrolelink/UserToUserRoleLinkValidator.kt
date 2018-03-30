package models.usertouserrolelink

import orm.usertouserrolelinkgeneratedrepository.UserToUserRoleLinkValidatorTrait

class UserToUserRoleLinkValidator(model: UserToUserRoleLink) : UserToUserRoleLinkValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}