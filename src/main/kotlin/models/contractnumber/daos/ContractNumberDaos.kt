package models.contractnumber.daos

import org.jooq.generated.tables.ContractNumbers
import models.contractnumber.daos.ContractNumberShowDao
import models.contractnumber.daos.ContractNumberIndexDao
import models.contractnumber.daos.ContractNumberEditDao
import models.contractnumber.daos.ContractNumberUpdateDao
import models.contractnumber.daos.ContractNumberDestroyDao

object ContractNumberDaos {

    val show = ContractNumberShowDao

    val index = ContractNumberIndexDao

    val edit = ContractNumberEditDao

    val update = ContractNumberUpdateDao

    val destroy = ContractNumberDestroyDao

}