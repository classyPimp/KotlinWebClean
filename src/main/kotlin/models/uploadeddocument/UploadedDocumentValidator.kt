package models.uploadeddocument

import orm.uploadeddocumentgeneratedrepository.UploadedDocumentValidatorTrait

class UploadedDocumentValidator(model: UploadedDocument) : UploadedDocumentValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

    fun createForPersonToCounterPartyLinkToUploadedDocumentLink() {
        validateFile()
    }

    private fun validateFile(){
        if (model.fileName == null) {
            validationManager.addError("file", "no file given")
            return
        }
        model.fileSize?.let {
            if (it > 1024 * 1024 * 5) {
                validationManager.addError("file", "size should not exceed 5 mb")
            }
        }
    }

}