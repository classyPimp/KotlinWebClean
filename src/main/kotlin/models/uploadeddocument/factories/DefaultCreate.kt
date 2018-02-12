package models.uploadeddocument.factories

import models.uploadeddocument.UploadedDocument
import models.uploadeddocument.UploadedDocumentRequestParametersWrapper

/**
 * Created by Муса on 16.01.2018.
 */
object DefaultCreate {

    fun create(params: UploadedDocumentRequestParametersWrapper, maxFileSize: Long =  1024 * 1024 * 10): UploadedDocument {
        return UploadedDocument().also {
            uploadedDocument ->
            uploadedDocument.file.maxAllowedSize = maxFileSize
            params.file?.let {
                uploadedDocument.file.assign(it)
            }
            uploadedDocument.id = params.id
        }
    }

}