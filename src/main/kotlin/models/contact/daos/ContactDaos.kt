package models.contact.daos

import org.jooq.generated.tables.Contacts
import models.contact.daos.ContactShowDao
import models.contact.daos.ContactIndexDao
import models.contact.daos.ContactEditDao
import models.contact.daos.ContactUpdateDao
import models.contact.daos.ContactDestroyDao

object ContactDaos {

    val show = ContactShowDao

    val index = ContactIndexDao

    val edit = ContactEditDao

    val update = ContactUpdateDao

    val destroy = ContactDestroyDao

}