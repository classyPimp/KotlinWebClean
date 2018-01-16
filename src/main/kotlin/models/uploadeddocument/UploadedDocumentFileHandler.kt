package models.uploadeddocument

import org.apache.commons.fileupload.FileItem
import orm.modelUtils.FileItemFileProperty
import java.io.File

class UploadedDocumentFileHandler(val model: UploadedDocument) : FileItemFileProperty() {

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

    override val fileNameOnProperty: String?
        get() = model.fileName

    override fun afterAssignedToUnpersitedModelAndItsSaved(file: File) {
        model.record.save()
    }

    override fun handlePropertiesOnAssign(fileItem: FileItem) {
        model.record.let {
            fileItem.let {
                fileItem ->
                it.fileName = fileItem.name
                it.fileSize = fileItem.size.toInt()
                it.fileMime = App.component.mimeDetector().detect(fileItem.inputStream)
            }
        }
    }

    override fun handlePropertiesOnDelete() {
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