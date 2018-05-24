package models.uploadedfilepermission

import org.jooq.generated.tables.UploadedFilePermissions
import orm.annotations.*
import orm.uploadedfilepermissiongeneratedrepository.UploadedFilePermissionRecord
import java.sql.Timestamp
import org.jooq.generated.tables.UserDefinableLinkReasons
import models.userdefinablelinkreason.UserDefinableLinkReason

@IsModel(jooqTable = UploadedFilePermissions::class)
class UploadedFilePermission {

    val record: UploadedFilePermissionRecord by lazy { UploadedFilePermissionRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "UPLOADED_FILE_ID")
    var uploadedFileId: Long? = null

    @TableField(name = "IS_OWNER")
    var isOwner: Boolean? = null

    @TableField(name = "PERMISSION_LEVEL")
    var permissionLevel: Int? = null

    @TableField(name = "PERMITTED_TO_ID")
    var permittedToId: Long? = null

    @TableField(name = "PERMITTED_TO_TYPE")
    var permittedToType: String? = null

    @TableField(name = "ACCESS_HASH_CODE")
    var accessHashCode: String? = null

    @TableField(name = "PRIMARY_PERMITTED_TO_ID")
    var primaryPermittedToId: Long? = null

    @TableField(name = "PRIMARY_PERMITTED_TO_TYPE")
    var primaryPermittedToType: String? = null

    @TableField(name = "HARDCODED_LINK_REASON")
    var hardcodedLinkReason: String? = null

    @TableField(name = "USER_DEFINABLE_LINK_REASON_ID")
    var userDefinableLinkReasonId: Long? = null

    @BelongsTo(model = UserDefinableLinkReason::class, fieldOnThis = "USER_DEFINABLE_LINK_REASON_ID", fieldOnThat = "ID")
    var userDefinableLinkReason: UserDefinableLinkReason? = null


}

