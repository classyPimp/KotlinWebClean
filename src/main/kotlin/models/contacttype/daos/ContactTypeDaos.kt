package models.contacttype.daos

import org.jooq.generated.tables.ContactTypes
import models.contacttype.daos.ContactTypeShowDao
import models.contacttype.daos.ContactTypeIndexDao
import models.contacttype.daos.ContactTypeEditDao
import models.contacttype.daos.ContactTypeUpdateDao
import models.contacttype.daos.ContactTypeDestroyDao

object ContactTypeDaos {

    val show = ContactTypeShowDao

    val index = ContactTypeIndexDao

    val edit = ContactTypeEditDao

    val update = ContactTypeUpdateDao

    val destroy = ContactTypeDestroyDao

}