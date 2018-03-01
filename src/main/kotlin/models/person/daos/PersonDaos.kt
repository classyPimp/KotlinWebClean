package models.person.daos

import org.jooq.generated.tables.People
import models.person.daos.PersonShowDao
import models.person.daos.PersonIndexDao
import models.person.daos.PersonEditDao
import models.person.daos.PersonUpdateDao
import models.person.daos.PersonDestroyDao

object PersonDaos {

    val show = PersonShowDao

    val index = PersonIndexDao

    val edit = PersonEditDao

    val update = PersonUpdateDao

    val destroy = PersonDestroyDao

}