package models.discussion

import orm.discussiongeneratedrepository.DiscussionValidatorTrait

class DiscussionValidator(model: Discussion) : DiscussionValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}