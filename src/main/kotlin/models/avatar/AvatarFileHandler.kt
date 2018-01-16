package models.avatar

import org.apache.commons.fileupload.FileItem
import orm.modelUtils.FileItemFileProperty
import java.io.File

class AvatarFileHandler(val model: Avatar) : FileItemFileProperty() {
    override val baseUploadPath: String = "uploads"
    override val modelName = "avatars"
    override val propertyName: String ="file"

    override val modelId: Long?
        get() =  model.id

    override val fileNameOnProperty: String?
        get() = model.avatarFileName

    override fun handlePropertiesOnAssign(fileItem: FileItem) {
        model.record.let {
            it.avatarFileName = fileItem.name
        }
    }

    override fun handlePropertiesOnDelete() {
        model.let {
            it.record.avatarFileName = null
        }
    }

    override fun preprocessFile(file: File) {
        App.component.imageProcessorFactory().create(file, createEmptyFileWherePreprocessedFileWillBeStored("avatarSize")).let {
            it.resize(600, 600)
            it.execute()
        }
    }

    override fun validateFile(uploadedFile: File): Boolean {
        return true
    }

    override fun afterAssignedToUnpersitedModelAndItsSaved(file: File) {
        model.record.save()
    }
}