package models.documenttemplate

import models.documenttemplate.DocumentTemplate
import models.uploadeddocument.UploadedDocumentRequestParametersWrapper
import utils.requestparameters.IParam

class DocumentTemplateRequestParametersWrapper(val requestParameters: IParam) {

    val uploadedDocument = requestParameters.get("uploadedDocument")?.let {
        UploadedDocumentRequestParametersWrapper(it)
    }

}