package models.uploadeddocument

import models.uploadeddocument.UploadedDocument
import org.apache.commons.fileupload.FileItem
import utils.requestparameters.IParam
import java.io.File

class UploadedDocumentRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? = requestParameters.get("id")?.long()
    val file: FileItem? = requestParameters.get("file")?.fileItem()

}