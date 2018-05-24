package models.generictouploadedfilelink

import models.genericmodeltouploadedfilelink.GenericToUploadedFileLink
import orm.generictouploadedfilelinkgeneratedrepository.GenericToUploadedFileLinkValidatorTrait

class GenericToUploadedFileLinkValidator(model: GenericToUploadedFileLink) : GenericToUploadedFileLinkValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}