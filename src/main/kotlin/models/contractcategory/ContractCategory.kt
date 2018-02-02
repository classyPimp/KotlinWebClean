package models.contractcategory

import models.contract.Contract
import org.jooq.generated.tables.ContractCategories
import orm.annotations.*
import orm.contractcategorygeneratedrepository.ContractCategoryRecord
import java.sql.Timestamp

@IsModel(jooqTable = ContractCategories::class)
class ContractCategory {

    val record: ContractCategoryRecord by lazy { ContractCategoryRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "NAME")
    var name: String? = null

    @TableField(name = "DESCRIPTION")
    var description: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasMany(model = Contract::class, fieldOnThat = "CONTRACT_CATEGORY_ID", fieldOnThis = "ID")
    var contracts: MutableList<Contract>? = null

}

