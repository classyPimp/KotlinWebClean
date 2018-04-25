package models.genericmodeltouploadedfilerelation

import org.jooq.generated.tables.GenericModelToUploadedFileRelations
import orm.annotations.*
import orm.genericmodeltouploadedfilerelationgeneratedrepository.GenericModelToUploadedFileRelationRecord
import java.sql.Timestamp
import org.jooq.generated.tables.UserDefinableRelationReasons
import models.userdefinablerelationreason.UserDefinableRelationReason
import org.jooq.generated.tables.UploadedFiles
import models.uploadedfile.UploadedFile

@IsModel(jooqTable = GenericModelToUploadedFileRelations::class)
class GenericModelToUploadedFileRelation {

    val record: GenericModelToUploadedFileRelationRecord by lazy { GenericModelToUploadedFileRelationRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "RELATED_MODEL_ID")
    var relatedModelId: Long? = null

    @TableField(name = "RELATED_MODEL_TYPE")
    var RelatedModelType: String? = null

    @TableField(name = "PRIMARY_RELATED_MODEL_ID")
    var primaryRelatedModelId: Long? = null

    @TableField(name = "PRIMARY_RELATED_MODEL_TYPE")
    var PrimaryRelatedModelType: String? = null

    @TableField(name = "PRIMARY_HARDCODED_RELATION_REASON")
    var primaryHardcodedRelationReason: Int? = null

    @TableField(name = "PRIMARY_USER_DEFINABLE_RELATION_REASON_ID")
    var primaryUserDefinableRelationReasonId: Long? = null

    @TableField(name = "UPLOADED_FILE_IS_FOLDER")
    var uploadedFileIsFolder: Boolean? = null

    @TableField(name = "HARDCODED_RELATION_REASON")
    var hardcodedRelationReason: Int? = null

    @TableField(name = "USER_DEFINABLE_RELATION_REASON_ID")
    var userDefinableRelationReasonId: Long? = null

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

    @BelongsTo(model = UserDefinableRelationReason::class, fieldOnThis = "PRIMARY_USER_DEFINABLE_RELATION_REASON_ID", fieldOnThat = "ID")
    var primaryUserDefinableRelationReason: UserDefinableRelationReason? = null

    @BelongsTo(model = UserDefinableRelationReason::class, fieldOnThis = "USER_DEFINABLE_RELATION_REASON_ID", fieldOnThat = "ID")
    var userDefinableRelationReason: UserDefinableRelationReason? = null

    @BelongsTo(model = UploadedFile::class, fieldOnThis = "UPLOADED_FILE_ID", fieldOnThat = "ID")
    var uploadedFile: UploadedFile? = null


}

