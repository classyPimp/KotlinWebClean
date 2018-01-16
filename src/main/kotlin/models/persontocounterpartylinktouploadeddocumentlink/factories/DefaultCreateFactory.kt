package models.persontocounterpartylinktouploadeddocumentlink.factories

import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLinkRequestParametersWrapper
import models.uploadeddocument.UploadedDocument
import models.uploadeddocument.factories.UploadedDocumentFactories

object DefaultCreateFactory {

    fun create(params: PersonToCounterPartyLinkToUploadedDocumentLinkRequestParametersWrapper): PersonToCounterPartyLinkToUploadedDocumentLink {
        return PersonToCounterPartyLinkToUploadedDocumentLink().also {
            it.uploadedDocument = UploadedDocumentFactories.forPersonToCounterPartyToUploadedDocumentLinkCreate.create(params.uploadedDocument)
        }
    }

}