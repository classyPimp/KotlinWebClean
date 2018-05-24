package models.generictouploadedfilelink.daos

import org.jooq.generated.tables.GenericToUploadedFileLinks

object GenericToUploadedFileLinkDaos {

    val show = GenericToUploadedFileLinkShowDao

    val index = GenericToUploadedFileLinkIndexDao

    val edit = GenericToUploadedFileLinkEditDao

    val update = GenericToUploadedFileLinkUpdateDao

    val destroy = GenericToUploadedFileLinkDestroyDao

}