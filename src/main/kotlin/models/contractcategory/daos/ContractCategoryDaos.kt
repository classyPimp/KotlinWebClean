package models.contractcategory.daos

import org.jooq.generated.tables.ContractCategories
import models.contractcategory.daos.ContractCategoryShowDao
import models.contractcategory.daos.ContractCategoryIndexDao
import models.contractcategory.daos.ContractCategoryUpdateDao
import models.contractcategory.daos.ContractCategoryDestroyDao

object ContractCategoryDaos {

    val show = ContractCategoryShowDao

    val index = ContractCategoryIndexDao

    val update = ContractCategoryUpdateDao

    val destroy = ContractCategoryDestroyDao

}