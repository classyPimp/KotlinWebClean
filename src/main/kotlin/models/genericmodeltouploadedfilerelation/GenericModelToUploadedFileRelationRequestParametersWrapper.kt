package models.genericmodeltouploadedfilerelation

import models.genericmodeltouploadedfilerelation.GenericModelToUploadedFileRelation
import utils.requestparameters.IParam

import java.sql.Timestamp

import org.jooq.generated.tables.UserDefinableRelationReasons
import models.userdefinablerelationreason.UserDefinableRelationReason
import models.userdefinablerelationreason.UserDefinableRelationReasonRequestParametersWrapper
import org.jooq.generated.tables.UploadedFiles
import models.uploadedfile.UploadedFile
import models.uploadedfile.UploadedFileRequestParametersWrapper

class GenericModelToUploadedFileRelationRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val relatedModelId: Long? by lazy { requestParameters.get("relatedModelId")?.long }
    val RelatedModelType: String? by lazy { requestParameters.get("RelatedModelType")?.string }
    val primaryRelatedModelId: Long? by lazy { requestParameters.get("primaryRelatedModelId")?.long }
    val PrimaryRelatedModelType: String? by lazy { requestParameters.get("PrimaryRelatedModelType")?.string }
    val primaryHardcodedRelationReason: String? by lazy { requestParameters.get("primaryHardcodedRelationReason")?.string }
    val primaryUserDefinableRelationReasonId: Long? by lazy { requestParameters.get("primaryUserDefinableRelationReasonId")?.long }
    val uploadedFileIsFolder: Boolean? by lazy { requestParameters.get("uploadedFileIsFolder")?.boolean }
    val hardcodedRelationReason: String? by lazy { requestParameters.get("hardcodedRelationReason")?.string }
    val userDefinableRelationReasonId: Long? by lazy { requestParameters.get("userDefinableRelationReasonId")?.long }
    val uploadedFileId: Long? by lazy { requestParameters.get("uploadedFileId")?.long }
    val fileIsSoftDeleted: Timestamp? by lazy { requestParameters.get("fileIsSoftDeleted")?.timestamp }
    val fileIsPermanentlyDeleted: Timestamp? by lazy { requestParameters.get("fileIsPermanentlyDeleted")?.timestamp }
    val accessLevel: Int? by lazy { requestParameters.get("accessLevel")?.int }
    val accessHashCode: String? by lazy { requestParameters.get("accessHashCode")?.string }
    val subIdentifier: String? by lazy { requestParameters.get("subIdentifier")?.string }
    val arbitraryTextInformation: String? by lazy { requestParameters.get("arbitraryTextInformation")?.string }
    val primaryUserDefinableRelationReason: UserDefinableRelationReasonRequestParametersWrapper? by lazy {
        requestParameters.get("primaryUserDefinableRelationReason")?.let {
            UserDefinableRelationReasonRequestParametersWrapper(it)
        }
    }
    val userDefinableRelationReason: UserDefinableRelationReasonRequestParametersWrapper? by lazy {
        requestParameters.get("userDefinableRelationReason")?.let {
            UserDefinableRelationReasonRequestParametersWrapper(it)
        }
    }
    val uploadedFile: UploadedFileRequestParametersWrapper? by lazy {
        requestParameters.get("uploadedFile")?.let {
            UploadedFileRequestParametersWrapper(it)
        }
    }


}