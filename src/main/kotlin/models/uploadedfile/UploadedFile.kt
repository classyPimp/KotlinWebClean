package models.uploadedfile

import orm.annotations.*
import orm.uploadedfilegeneratedrepository.UploadedFileRecord
import java.sql.Timestamp
import org.jooq.generated.tables.UploadedFiles
import models.uploadedfile.UploadedFile

@IsModel(jooqTable = UploadedFiles::class)
class UploadedFile {

    val record: UploadedFileRecord by lazy { UploadedFileRecord(this) }

    val file: UploadedFileFileHandler by lazy { UploadedFileFileHandler(this) }
        @DelegatesGetter
        get

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "FILE_NAME")
    var fileName: String? = null

    @TableField(name = "HARDCODED_CATEGORY")
    var hardcodedCategory: Int? = null

    @TableField(name = "FILE_SIZE")
    var fileSize: Long? = null

    @TableField(name = "FOLDER_ID")
    var folderId: Long? = null

    @TableField(name = "SHORTCUT_TO_ID")
    var shortCutToId: Long? = null

    @TableField(name = "IS_SHORTCUT_TO_FOLDER")
    var isShortCutToFolder: Boolean? = null

    @TableField(name = "FILE_MIME")
    var fileMime: String? = null

    @TableField(name= "SOFT_DELETED_SINCE")
    var softDeletedSince: Timestamp? = null

    @HasMany(model = UploadedFile::class, fieldOnThis = "ID", fieldOnThat = "FOLDER_ID")
    var uploadedFiles: MutableList<UploadedFile>? = null

    @BelongsTo(model = UploadedFile::class, fieldOnThis = "FOLDER_ID", fieldOnThat = "ID")
    var parentFolder: UploadedFile? = null

    @BelongsTo(model = UploadedFile::class, fieldOnThis = "SHORTCUT_TO_ID", fieldOnThat = "ID")
    var shortcutTo: UploadedFile? = null

    @HasMany(model = UploadedFile::class, fieldOnThis = "ID", fieldOnThat = "SHORTCUT_TO_ID")
    var shortcuts: MutableList<UploadedFile>? = null


}

