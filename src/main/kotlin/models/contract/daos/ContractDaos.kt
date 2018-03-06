package models.contract.daos

import org.jooq.generated.tables.Contracts
import models.contract.daos.ContractShowDao
import models.contract.daos.ContractIndexDao
import models.contract.daos.ContractUpdateDao
import models.contract.daos.ContractDestroyDao

object ContractDaos {

    val show = ContractShowDao
    val index = ContractIndexDao
    val update = ContractUpdateDao
    val destroy = ContractDestroyDao



}