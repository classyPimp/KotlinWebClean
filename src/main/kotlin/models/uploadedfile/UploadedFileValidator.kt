package models.uploadedfile

import orm.uploadedfilegeneratedrepository.UploadedFileValidatorTrait

class UploadedFileValidator(model: UploadedFile) : UploadedFileValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}