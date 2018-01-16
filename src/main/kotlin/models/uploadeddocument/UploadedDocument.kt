package models.uploadeddocument

import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import org.jooq.generated.tables.UploadedDocuments
import orm.annotations.*
import orm.uploadeddocumentgeneratedrepository.UploadedDocumentRecord
import java.sql.Timestamp

/**
 * Created by Муса on 05.12.2017.
 */
@IsModel(jooqTable = UploadedDocuments::class)
class UploadedDocument {

    val record: UploadedDocumentRecord by lazy { UploadedDocumentRecord(this) }

    val file: UploadedDocumentFileHandler by lazy { UploadedDocumentFileHandler(this) }
        @DelegatesGetter
        get

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "UPLOADED_DOCUMENT_ID")
    var uploadedDocumentId: Long? = null

    @TableField(name = "DESCRIPTION")
    var description: String? = null

    @TableField(name = "FILE_NAME")
    var fileName: String? = null

    @TableField(name = "FILE_SIZE")
    var fileSize: Int? = null

    @TableField(name = "FILE_MIME")
    var fileMime: String? = null

    @TableField(name = "IS_FOLDER")
    var isFolder: Boolean? = null

    @TableField(name = "FOLDER_NAME")
    var folderName: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasMany(model = UploadedDocument::class, fieldOnThis = "ID", fieldOnThat = "UPLOADED_DOCUMENT_ID")
    var childDocuments: MutableList<UploadedDocument>? = null

    @BelongsTo(model=  UploadedDocument::class, fieldOnThat = "ID", fieldOnThis = "UPLOADED_DOCUMENT_ID")
    var parentDocument: UploadedDocument? = null

    @HasMany(model = PersonToCounterPartyLinkToUploadedDocumentLink::class, fieldOnThis = "ID", fieldOnThat = "UPLOADED_DOCUMENT_ID")
    var personToCounterPartyLinkToUploadedDocumentLinks: MutableList<PersonToCounterPartyLinkToUploadedDocumentLink>? = null

}