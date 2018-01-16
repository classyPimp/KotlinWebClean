package models.persontocounterpartylinktouploadeddocumentlink

import models.uploadeddocument.UploadedDocument
import models.uploadeddocument.UploadedDocumentValidator
import orm.persontocounterpartylinktouploadeddocumentlinkgeneratedrepository.PersonToCounterPartyLinkToUploadedDocumentLinkValidatorTrait

class PersonToCounterPartyLinkToUploadedDocumentLinkValidator(model: PersonToCounterPartyLinkToUploadedDocumentLink) : PersonToCounterPartyLinkToUploadedDocumentLinkValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validatePersonToCounterPartyLinkId()
        validateUploadedDocument()
    }

    private fun validatePersonToCounterPartyLinkId(){
        model.personToCounterPartyLink ?: validationManager.addGeneralError("no such link")
    }

    private fun validateUploadedDocument() {
        model.uploadedDocument?.let {
            UploadedDocumentValidator(it).createForPersonToCounterPartyLinkToUploadedDocumentLink()
            if (!it.record.validationManager.isValid()) {
                validationManager.addError("nested", "nested errors")
            }
        } ?: validationManager.addGeneralError("no file assigned")

    }

}