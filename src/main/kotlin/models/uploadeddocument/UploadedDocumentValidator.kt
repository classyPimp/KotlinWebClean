package models.uploadeddocument

import orm.uploadeddocumentgeneratedrepository.UploadedDocumentValidatorTrait

class UploadedDocumentValidator(model: UploadedDocument) : UploadedDocumentValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}