package models.monetaryobligation.daos

import org.jooq.generated.tables.MonetaryObligations
import models.monetaryobligation.daos.MonetaryObligationShowDao
import models.monetaryobligation.daos.MonetaryObligationIndexDao
import models.monetaryobligation.daos.MonetaryObligationEditDao
import models.monetaryobligation.daos.MonetaryObligationUpdateDao
import models.monetaryobligation.daos.MonetaryObligationDestroyDao

object MonetaryObligationDaos {

    val show = MonetaryObligationShowDao

    val index = MonetaryObligationIndexDao

    val edit = MonetaryObligationEditDao

    val update = MonetaryObligationUpdateDao

    val destroy = MonetaryObligationDestroyDao

}