package models.genericmodeltouploadedfilelink

import org.jooq.generated.tables.GenericToUploadedFileLinks
import orm.annotations.*
import orm.generictouploadedfilelinkgeneratedrepository.GenericToUploadedFileLinkRecord
import java.sql.Timestamp
import org.jooq.generated.tables.UserDefinableLinkReasons
import models.userdefinablelinkreason.UserDefinableLinkReason
import models.uploadedfile.UploadedFile

@IsModel(jooqTable = GenericToUploadedFileLinks::class)
class GenericToUploadedFileLink {

    val record: GenericToUploadedFileLinkRecord by lazy { GenericToUploadedFileLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "LINKED_MODEL_ID")
    var linkedModelId: Long? = null

    @TableField(name = "LINKED_MODEL_TYPE")
    var linkedModelType: String? = null

    @TableField(name = "PRIMARY_LINKED_MODEL_ID")
    var primaryLinkedModelId: Long? = null

    @TableField(name = "PRIMARY_LINKED_MODEL_TYPE")
    var PrimaryLinkedModelType: String? = null

    @TableField(name = "PRIMARY_HARDCODED_LINK_REASON")
    var primaryHardcodedLinkReason: Int? = null

    @TableField(name = "PRIMARY_USER_DEFINABLE_LINK_REASON_ID")
    var primaryUserDefinableLinkReasonId: Long? = null

    @TableField(name = "UPLOADED_FILE_IS_FOLDER")
    var uploadedFileIsFolder: Boolean? = null

    @TableField(name = "SECONDARY_HARDCODED_LINK_REASON")
    var hardcodedLinkReason: Int? = null

    @TableField(name = "SECONDARY_USER_DEFINABLE_LINK_REASON_ID")
    var secondaryUserDefinableLinkReasonId: Long? = null

    @TableField(name = "UPLOADED_FILE_ID")
    var uploadedFileId: Long? = null

    @TableField(name = "FILE_IS_SOFT_DELETED_SINCE")
    var fileIsSoftDeleted: Timestamp? = null

    @TableField(name = "FILE_IS_PERMANENTLY_DELETED_SINCE")
    var fileIsPermanentlyDeleted: Timestamp? = null

    @TableField(name = "ACCESS_LEVEL")
    var accessLevel: Int? = null

    @TableField(name = "ACCESS_HASH_CODE")
    var accessHashCode: String? = null

    @TableField(name = "SUB_IDENTIFIER")
    var subIdentifier: String? = null

    @TableField(name = "ARBITRARY_TEXT_INFORMATION")
    var arbitraryTextInformation: String? = null

    @BelongsTo(model = UserDefinableLinkReason::class, fieldOnThis = "PRIMARY_USER_DEFINABLE_LINK_REASON_ID", fieldOnThat = "ID")
    var primaryUserDefinableLinkReason: UserDefinableLinkReason? = null

    @BelongsTo(model = UserDefinableLinkReason::class, fieldOnThis = "SECONDARY_USER_DEFINABLE_LINK_REASON_ID", fieldOnThat = "ID")
    var secondaryUserDefinableLinkReason: UserDefinableLinkReason? = null

    @BelongsTo(model = UploadedFile::class, fieldOnThis = "UPLOADED_FILE_ID", fieldOnThat = "ID")
    var uploadedFile: UploadedFile? = null


}

