package models.person

import orm.persongeneratedrepository.PersonValidatorTrait

class PersonValidator(model: Person) : PersonValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateName()
    }

    fun udpateScenario() {
        createScenario()
    }

    private fun validateName() {
        if (model.name.isNullOrBlank()) {
            model.record.validationManager.addNameError("name not provided")
            return
        }
        nameTester().let {
            test -> {
                test.shouldBeLongerThan(model.name!!, 1)
            }
        }
    }

}