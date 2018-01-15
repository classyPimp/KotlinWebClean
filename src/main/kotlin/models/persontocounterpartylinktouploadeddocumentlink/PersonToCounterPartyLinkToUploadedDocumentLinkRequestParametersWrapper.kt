package models.persontocounterpartylinktouploadeddocumentlink

import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import models.uploadeddocument.UploadedDocument
import models.uploadeddocument.UploadedDocumentRequestParametersWrapper
import utils.requestparameters.IParam

class PersonToCounterPartyLinkToUploadedDocumentLinkRequestParametersWrapper(val requestParameters: IParam) {

    val uploadedDocument: UploadedDocumentRequestParametersWrapper? = requestParameters.get("uploadedDocument")?.let {
        UploadedDocumentRequestParametersWrapper(it)
    }

}