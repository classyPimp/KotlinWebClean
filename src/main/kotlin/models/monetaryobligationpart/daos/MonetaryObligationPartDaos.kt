package models.monetaryobligationpart.daos

import org.jooq.generated.tables.MonetaryObligationParts
import models.monetaryobligationpart.daos.MonetaryObligationPartShowDao
import models.monetaryobligationpart.daos.MonetaryObligationPartIndexDao
import models.monetaryobligationpart.daos.MonetaryObligationPartEditDao
import models.monetaryobligationpart.daos.MonetaryObligationPartUpdateDao
import models.monetaryobligationpart.daos.MonetaryObligationPartDestroyDao

object MonetaryObligationPartDaos {

    val show = MonetaryObligationPartShowDao

    val index = MonetaryObligationPartIndexDao

    val edit = MonetaryObligationPartEditDao

    val update = MonetaryObligationPartUpdateDao

    val destroy = MonetaryObligationPartDestroyDao

}