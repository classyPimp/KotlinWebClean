package models.incorporationform

import orm.incorporationformgeneratedrepository.IncorporationFormValidatorTrait

class IncorporationFormValidator(model: IncorporationForm) : IncorporationFormValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}