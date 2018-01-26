package models.uploadeddocument

import org.apache.commons.fileupload.FileItem
import orm.modelUtils.FileItemFileProperty
import orm.modelUtils.FileProperty
import java.io.File

class UploadedDocumentFileHandler(val model: UploadedDocument) : FileProperty() {

    override var fileSize: Long?
        get() {
            return model.record.fileSize?.toLong()
        }
        set(value) {
            model.record.fileSize = value?.toInt()
        }

    override val maxAllowedSize: Long
        get() {
            return 1024 * 1024 * 10
        }

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

    override var fileName: String?
        get() = model.fileName
        set(value) {
            model.record.fileName = value
        }


    override fun onFileAssigned(file: File) {
        model.record.let {
            file.let {
                fileItem ->
                it.fileName = fileItem.name
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