package models.generictouploadedfilelink

import utils.requestparameters.IParam

import java.sql.Timestamp

import models.userdefinablelinkreason.UserDefinableLinkReasonRequestParametersWrapper
import models.uploadedfile.UploadedFileRequestParametersWrapper

class GenericToUploadedFileLinkRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val linkedModelId: Long? by lazy { requestParameters.get("linkedModelId")?.long }
    val linkedModelType: String? by lazy { requestParameters.get("linkedModelType")?.string }
    val primaryLinkedModelId: Long? by lazy { requestParameters.get("primaryLinkedModelId")?.long }
    val PrimaryLinkedModelType: String? by lazy { requestParameters.get("PrimaryLinkedModelType")?.string }
    val primaryHardcodedLinkReason: String? by lazy { requestParameters.get("primaryHardcodedLinkReason")?.string }
    val primaryUserDefinableLinkReasonId: Long? by lazy { requestParameters.get("primaryUserDefinableLinkReasonId")?.long }
    val uploadedFileIsFolder: Boolean? by lazy { requestParameters.get("uploadedFileIsFolder")?.boolean }
    val hardcodedLinkReason: String? by lazy { requestParameters.get("hardcodedLinkReason")?.string }
    val userDefinableLinkReasonId: Long? by lazy { requestParameters.get("userDefinableLinkReasonId")?.long }
    val uploadedFileId: Long? by lazy { requestParameters.get("uploadedFileId")?.long }
    val fileIsSoftDeleted: Timestamp? by lazy { requestParameters.get("fileIsSoftDeleted")?.timestamp }
    val fileIsPermanentlyDeleted: Timestamp? by lazy { requestParameters.get("fileIsPermanentlyDeleted")?.timestamp }
    val accessLevel: Int? by lazy { requestParameters.get("accessLevel")?.int }
    val accessHashCode: String? by lazy { requestParameters.get("accessHashCode")?.string }
    val subIdentifier: String? by lazy { requestParameters.get("subIdentifier")?.string }
    val arbitraryTextInformation: String? by lazy { requestParameters.get("arbitraryTextInformation")?.string }
    val primaryUserDefinableLinkReason: UserDefinableLinkReasonRequestParametersWrapper? by lazy {
        requestParameters.get("primaryUserDefinableLinkReason")?.let {
            UserDefinableLinkReasonRequestParametersWrapper(it)
        }
    }
    val userDefinableLinkReason: UserDefinableLinkReasonRequestParametersWrapper? by lazy {
        requestParameters.get("userDefinableLinkReason")?.let {
            UserDefinableLinkReasonRequestParametersWrapper(it)
        }
    }
    val uploadedFile: UploadedFileRequestParametersWrapper? by lazy {
        requestParameters.get("uploadedFile")?.let {
            UploadedFileRequestParametersWrapper(it)
        }
    }


}