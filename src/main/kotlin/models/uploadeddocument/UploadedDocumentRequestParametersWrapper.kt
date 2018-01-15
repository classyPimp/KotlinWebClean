package models.uploadeddocument

import models.uploadeddocument.UploadedDocument
import utils.requestparameters.IParam
import java.io.File

class UploadedDocumentRequestParametersWrapper(val requestParameters: IParam) {

    val file = requestParameters.get("file")?.fileItem()

}