package models.counterparty.daos

import org.jooq.generated.tables.CounterParties
import models.counterparty.daos.CounterPartyShowDao
import models.counterparty.daos.CounterPartyIndexDao
import models.counterparty.daos.CounterPartyEditDao
import models.counterparty.daos.CounterPartyUpdateDao
import models.counterparty.daos.CounterPartyDestroyDao

object CounterPartyDaos {

    val show = CounterPartyShowDao

    val index = CounterPartyIndexDao

    val edit = CounterPartyEditDao

    val update = CounterPartyUpdateDao

    val destroy = CounterPartyDestroyDao

}