package models.uploadedfilepermission

import utils.requestparameters.IParam

import java.sql.Timestamp

import org.jooq.generated.tables.UserDefinableLinkReasons
import models.userdefinablelinkreason.UserDefinableLinkReasonRequestParametersWrapper

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
    val hardcodedLinkReason: String? by lazy { requestParameters.get("hardcodedLinkReason")?.string }
    val userDefinableLinkReasonId: Long? by lazy { requestParameters.get("userDefinableLinkReasonId")?.long }
    val userDefinableLinkReason: UserDefinableLinkReasonRequestParametersWrapper? by lazy {
        requestParameters.get("userDefinableLinkReason")?.let {
            UserDefinableLinkReasonRequestParametersWrapper(it)
        }
    }


}