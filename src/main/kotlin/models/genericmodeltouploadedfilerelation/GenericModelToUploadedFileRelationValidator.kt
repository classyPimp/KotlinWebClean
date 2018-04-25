package models.genericmodeltouploadedfilerelation

import orm.genericmodeltouploadedfilerelationgeneratedrepository.GenericModelToUploadedFileRelationValidatorTrait

class GenericModelToUploadedFileRelationValidator(model: GenericModelToUploadedFileRelation) : GenericModelToUploadedFileRelationValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}