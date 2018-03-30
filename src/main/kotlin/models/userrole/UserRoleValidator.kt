package models.userrole

import orm.userrolegeneratedrepository.UserRoleValidatorTrait

class UserRoleValidator(model: UserRole) : UserRoleValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}