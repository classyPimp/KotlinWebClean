package models.uploadedfilepermission

import models.uploadedfilepermission.UploadedFilePermission
import utils.requestparameters.IParam

import java.sql.Timestamp

import org.jooq.generated.tables.UserDefinableRelationReasons
import models.userdefinablerelationreason.UserDefinableRelationReason
import models.userdefinablerelationreason.UserDefinableRelationReasonRequestParametersWrapper

class UploadedFilePermissionRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val uploadedFileId: Long? by lazy { requestParameters.get("uploadedFileId")?.long }
    val isOwner: Boolean? by lazy { requestParameters.get("isOwner")?.boolean }
    val permissionLevel: Int? by lazy { requestParameters.get("permissionLevel")?.int}
    val permittedToId: Long? by lazy { requestParameters.get("permittedToId")?.long }
    val permittedToType: String? by lazy { requestParameters.get("permittedToType")?.string }
    val accessHashCode: String? by lazy { requestParameters.get("accessHashCode")?.string }
    val primaryPermittedToId: Long? by lazy { requestParameters.get("primaryPermittedToId")?.long }
    val primaryPermittedToType: String? by lazy { requestParameters.get("primaryPermittedToType")?.string }
    val hardcodedRelationReason: String? by lazy { requestParameters.get("hardcodedRelationReason")?.string }
    val userDefinableRelationReasonId: Long? by lazy { requestParameters.get("userDefinableRelationReasonId")?.long }
    val userDefinableRelationReason: UserDefinableRelationReasonRequestParametersWrapper? by lazy {
        requestParameters.get("userDefinableRelationReason")?.let {
            UserDefinableRelationReasonRequestParametersWrapper(it)
        }
    }


}