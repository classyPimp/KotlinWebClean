package models.uploadeddocument

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

    override fun handlePropertiesOnAssign(file: File) {
        model.record.let {
            it.fileName = model.fileName
        }
    }

    override fun handlePropertiesOnDelete() {
        model.let {
            it.record.fileName = null
        }
    }

    override fun preprocessFile(file: File) {

    }

    override fun validateFile(uploadedFile: File): Boolean {
        return true
    }

}