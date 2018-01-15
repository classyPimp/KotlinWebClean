package models.uploadeddocument.daos

import org.jooq.generated.tables.UploadedDocuments
import models.uploadeddocument.daos.UploadedDocumentShowDao
import models.uploadeddocument.daos.UploadedDocumentIndexDao
import models.uploadeddocument.daos.UploadedDocumentEditDao
import models.uploadeddocument.daos.UploadedDocumentUpdateDao
import models.uploadeddocument.daos.UploadedDocumentDestroyDao

object UploadedDocumentDaos {

    val show = UploadedDocumentShowDao

    val index = UploadedDocumentIndexDao

    val edit = UploadedDocumentEditDao

    val update = UploadedDocumentUpdateDao

    val destroy = UploadedDocumentDestroyDao

}