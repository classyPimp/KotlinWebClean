package models.persontocounterpartylink.daos

import org.jooq.generated.tables.PersonToCounterPartyLinks
import models.persontocounterpartylink.daos.PersonToCounterPartyLinkShowDao
import models.persontocounterpartylink.daos.PersonToCounterPartyLinkIndexDao
import models.persontocounterpartylink.daos.PersonToCounterPartyLinkEditDao
import models.persontocounterpartylink.daos.PersonToCounterPartyLinkUpdateDao
import models.persontocounterpartylink.daos.PersonToCounterPartyLinkDestroyDao

object PersonToCounterPartyLinkDaos {

    val show = PersonToCounterPartyLinkShowDao

    val index = PersonToCounterPartyLinkIndexDao

    val edit = PersonToCounterPartyLinkEditDao

    val update = PersonToCounterPartyLinkUpdateDao

    val destroy = PersonToCounterPartyLinkDestroyDao

}