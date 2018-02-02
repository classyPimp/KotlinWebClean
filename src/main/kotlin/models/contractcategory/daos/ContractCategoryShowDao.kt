package models.contractcategory.daos

import org.jooq.generated.tables.ContractCategories.CONTRACT_CATEGORIES
import orm.contractcategorygeneratedrepository.ContractCategoryRecord
import models.contractcategory.ContractCategory

object ContractCategoryShowDao {

    val table = CONTRACT_CATEGORIES

    fun forShow(id: Long): ContractCategory? {
        return ContractCategoryRecord.GET()
                .where(
                        table.ID.eq(id)
                )
                .limit(1)
                .execute()
                .firstOrNull()
    }

    fun forEdit(id: Long): ContractCategory? {
        return forShow(id)
    }

    fun forDestroy(id: Long): ContractCategory? {
        return forShow(id)
    }

    fun forUpdate(id: Long): ContractCategory? {
        return forShow(id)
    }

    fun forCheckingIfWithSuchNameExists(name: String): ContractCategory? {
        return ContractCategoryRecord.GET()
                .where(
                        table.NAME.eq(name)
                )
                .limit(1)
                .execute()
                .firstOrNull()
    }


}