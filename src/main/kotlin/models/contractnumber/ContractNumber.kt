package models.contractnumber

import models.contract.Contract
import org.jooq.generated.tables.ContractNumbers
import orm.annotations.*
import orm.contractnumbergeneratedrepository.ContractNumberRecord
import java.sql.Timestamp

@IsModel(jooqTable = ContractNumbers::class)
class ContractNumber {

    val record: ContractNumberRecord by lazy { ContractNumberRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "INTERNAL_NUMBER")
    var internalNumber: String? = null

    @TableField(name = "NUMBER_ASSIGNED_BY_COUNTER_PARTY")
    var numberAssignedByCounterParty: String? = null

    @TableField(name = "ASSIGNED_NUMBER")
    var assignedNumber: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasOne(model = Contract::class, fieldOnThis = "ID", fieldOnThat = "CONTRACT_NUMBER_ID")
    var contract: Contract? = null

}

