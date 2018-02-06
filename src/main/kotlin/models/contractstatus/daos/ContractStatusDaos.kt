package models.contractstatus.daos

import org.jooq.generated.tables.ContractStatuses
import models.contractstatus.daos.ContractStatusShowDao
import models.contractstatus.daos.ContractStatusIndexDao
import models.contractstatus.daos.ContractStatusEditDao
import models.contractstatus.daos.ContractStatusUpdateDao
import models.contractstatus.daos.ContractStatusDestroyDao

object ContractStatusDaos {

    val show = ContractStatusShowDao

    val index = ContractStatusIndexDao

    val edit = ContractStatusEditDao

    val update = ContractStatusUpdateDao

    val destroy = ContractStatusDestroyDao

}