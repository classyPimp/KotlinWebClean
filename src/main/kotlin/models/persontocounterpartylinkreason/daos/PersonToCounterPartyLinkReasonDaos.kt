package models.persontocounterpartylinkreason.daos

import org.jooq.generated.tables.PersonToCounterPartyLinkReasons
import models.persontocounterpartylinkreason.daos.PersonToCounterPartyLinkReasonShowDao
import models.persontocounterpartylinkreason.daos.PersonToCounterPartyLinkReasonIndexDao
import models.persontocounterpartylinkreason.daos.PersonToCounterPartyLinkReasonEditDao
import models.persontocounterpartylinkreason.daos.PersonToCounterPartyLinkReasonUpdateDao
import models.persontocounterpartylinkreason.daos.PersonToCounterPartyLinkReasonDestroyDao

object PersonToCounterPartyLinkReasonDaos {

    val show = PersonToCounterPartyLinkReasonShowDao

    val index = PersonToCounterPartyLinkReasonIndexDao

    val edit = PersonToCounterPartyLinkReasonEditDao

    val update = PersonToCounterPartyLinkReasonUpdateDao

    val destroy = PersonToCounterPartyLinkReasonDestroyDao

}