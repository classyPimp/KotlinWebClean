package models.uploadedfilepermission.daos

import org.jooq.generated.tables.UploadedFilePermissions
import models.uploadedfilepermission.daos.UploadedFilePermissionShowDao
import models.uploadedfilepermission.daos.UploadedFilePermissionIndexDao
import models.uploadedfilepermission.daos.UploadedFilePermissionEditDao
import models.uploadedfilepermission.daos.UploadedFilePermissionUpdateDao
import models.uploadedfilepermission.daos.UploadedFilePermissionDestroyDao

object UploadedFilePermissionDaos {

    val show = UploadedFilePermissionShowDao

    val index = UploadedFilePermissionIndexDao

    val edit = UploadedFilePermissionEditDao

    val update = UploadedFilePermissionUpdateDao

    val destroy = UploadedFilePermissionDestroyDao

}