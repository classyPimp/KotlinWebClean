package models.uploadeddocument

import org.apache.commons.fileupload.FileItem
import orm.modelUtils.FileItemFileProperty
import orm.modelUtils.FileProperty
import java.io.File

class UploadedDocumentFileHandler(val model: UploadedDocument) : FileProperty() {

    override val fileNameOnModel: String?
        get() = model.fileName

    override var maxAllowedSize: Long = 1024 * 1024 * 10

    override val baseUploadPath: String
        get(){
            return "uploads"
        }

    override val modelName: String
        get() = "uploadedDocuments"

    override val propertyName: String
        get() = "file"

    override val modelId: Long?
        get() = model.id



    override fun onFileAssigned(file: File, targetFileName: String?) {
        model.record.let {
            file.let {
                fileItem ->
                it.fileName = targetFileName
                it.fileSize = fileItem.length().toInt()
                it.fileMime = App.component.mimeDetector().detect(file)
            }
        }
    }

    override fun onFileDelete() {
        model.record.let {
            it.fileName = null
            it.fileSize = null
            it.fileMime = null
        }
    }


    override fun preprocessFile(file: File) {

    }

    override fun validateFile(uploadedFile: File): Boolean {
        return true
    }

}