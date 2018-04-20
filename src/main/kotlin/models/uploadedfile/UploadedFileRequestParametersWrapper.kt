package models.uploadedfile

import models.uploadedfile.UploadedFile
import utils.requestparameters.IParam

import java.sql.Timestamp

import org.jooq.generated.tables.UploadedFiles
import models.uploadedfile.UploadedFileRequestParametersWrapper

class UploadedFileRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val fileName: String? by lazy { requestParameters.get("fileName")?.string }
    val fileSize: Long? by lazy { requestParameters.get("fileSize")?.long }
    val folderId: Long? by lazy { requestParameters.get("folderId")?.long }
    val shortCutToId: Long? by lazy { requestParameters.get("shortCutToId")?.long }
    val isShortCutToFolder: Boolean? by lazy { requestParameters.get("isShortCutToFolder")?.boolean }
    val fileMime: String? by lazy { requestParameters.get("fileMime")?.string }
    val softDeletedSince: Timestamp? by lazy { requestParameters.get("softDeletedSince")?.timestamp }
    val uploadedFiles: MutableList<UploadedFileRequestParametersWrapper>? by lazy {
    requestParameters.get("uploadedFiles")?.paramList()?.let {
        it.mapTo(mutableListOf<UploadedFileRequestParametersWrapper>()) {
            UploadedFileRequestParametersWrapper(it)
        }
    }
    }
    val parentFolder: UploadedFileRequestParametersWrapper? by lazy {
        requestParameters.get("parentFolder")?.let {
            UploadedFileRequestParametersWrapper(it)
        }
    }
    val shortcutTo: UploadedFileRequestParametersWrapper? by lazy {
        requestParameters.get("shortcutTo")?.let {
            UploadedFileRequestParametersWrapper(it)
        }
    }
    val shortcuts: MutableList<UploadedFileRequestParametersWrapper>? by lazy {
    requestParameters.get("shortcuts")?.paramList()?.let {
        it.mapTo(mutableListOf<UploadedFileRequestParametersWrapper>()) {
            UploadedFileRequestParametersWrapper(it)
        }
    }
    }


}