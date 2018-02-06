package models.contracttocounterpartylink.daos

import org.jooq.generated.tables.ContractToCounterPartyLinks
import models.contracttocounterpartylink.daos.ContractToCounterPartyLinkShowDao
import models.contracttocounterpartylink.daos.ContractToCounterPartyLinkIndexDao
import models.contracttocounterpartylink.daos.ContractToCounterPartyLinkEditDao
import models.contracttocounterpartylink.daos.ContractToCounterPartyLinkUpdateDao
import models.contracttocounterpartylink.daos.ContractToCounterPartyLinkDestroyDao

object ContractToCounterPartyLinkDaos {

    val show = ContractToCounterPartyLinkShowDao

    val index = ContractToCounterPartyLinkIndexDao

    val edit = ContractToCounterPartyLinkEditDao

    val update = ContractToCounterPartyLinkUpdateDao

    val destroy = ContractToCounterPartyLinkDestroyDao

}