package models.incorporationform.daos

import org.jooq.generated.tables.IncorporationForms
import models.incorporationform.daos.IncorporationFormShowDao
import models.incorporationform.daos.IncorporationFormIndexDao
import models.incorporationform.daos.IncorporationFormEditDao
import models.incorporationform.daos.IncorporationFormUpdateDao
import models.incorporationform.daos.IncorporationFormDestroyDao

object IncorporationFormDaos {

    val show = IncorporationFormShowDao

    val index = IncorporationFormIndexDao

    val edit = IncorporationFormEditDao

    val update = IncorporationFormUpdateDao

    val destroy = IncorporationFormDestroyDao

}