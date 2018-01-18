package models.contract

import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import org.jooq.generated.tables.Contracts
import orm.annotations.*
import orm.contractgeneratedrepository.ContractRecord
import java.sql.Timestamp

@IsModel(jooqTable = Contracts::class)
class Contract {

    val record: ContractRecord by lazy { ContractRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "DESCRIPTION")
    var description: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasMany(model = ContractToUploadedDocumentLink::class, fieldOnThat = "CONTRACT_ID", fieldOnThis = "ID")
    var contractToUploadedDocumentLinks: MutableList<ContractToUploadedDocumentLink>? = null

}

