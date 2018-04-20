package models.uploadedfile.daos

import org.jooq.generated.tables.UploadedFiles
import models.uploadedfile.daos.UploadedFileShowDao
import models.uploadedfile.daos.UploadedFileIndexDao
import models.uploadedfile.daos.UploadedFileEditDao
import models.uploadedfile.daos.UploadedFileUpdateDao
import models.uploadedfile.daos.UploadedFileDestroyDao

object UploadedFileDaos {

    val show = UploadedFileShowDao

    val index = UploadedFileIndexDao

    val edit = UploadedFileEditDao

    val update = UploadedFileUpdateDao

    val destroy = UploadedFileDestroyDao

}