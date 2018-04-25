package models.jobpositiontouserlink

import orm.jobpositiontouserlinkgeneratedrepository.JobPositionToUserLinkValidatorTrait

class JobPositionToUserLinkValidator(model: JobPositionToUserLink) : JobPositionToUserLinkValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}