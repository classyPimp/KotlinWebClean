package models.contractcategory.daos

import org.jooq.generated.tables.ContractCategories
import orm.contractcategorygeneratedrepository.ContractCategoryRecord
import models.contractcategory.ContractCategory

object ContractCategoryIndexDao {
    fun forIndex(): MutableList<ContractCategory> {
        return ContractCategoryRecord.GET()
                .execute()
    }


}