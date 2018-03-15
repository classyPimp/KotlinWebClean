package models.contractstatus

import models.contract.Contract
import org.jooq.generated.tables.ContractStatuses
import orm.annotations.*
import orm.contractstatusgeneratedrepository.ContractStatusRecord
import java.sql.Timestamp
import java.util.*

@IsModel(jooqTable = ContractStatuses::class)
class ContractStatus {

    companion object {
        fun generateNumber(): String {
            val SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
            val salt = StringBuilder()
            val rnd = Random()
            while (salt.length < 6) { // length of the random string.
                val index = (rnd.nextFloat() * SALTCHARS.length).toInt()
                salt.append(SALTCHARS[index])
            }
            val generated =  salt.toString()
            return generated
        }
    }

    val record: ContractStatusRecord by lazy { ContractStatusRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "INTERNAL_NUMBER")
    var internalNumber: String? = null

    @TableField(name = "ASSIGNED_NUMBER")
    var assignedNumber: String? = null

    @TableField(name = "FORMAL_DATE")
    var formalDate: Timestamp? = null

    @TableField(name = "IS_COMMITTED")
    var isCommitted: Boolean? = null

    @TableField(name = "IS_SUPPLEMENT")
    var isSupplement: Boolean? = null

    @TableField(name = "PARENT_CONTRACT_ID")
    var parentContractId: Long? = null

    @TableField(name = "ROOT_CONTRACT_ID")
    var rootContractId: Long? = null

    @TableField(name = "IS_SUPPLEMENTED")
    var isSupplemented: Boolean? = null

    @TableField(name = "IS_PROJECT")
    var isProject: Boolean? = null

    @TableField(name = "IS_CANCELLED")
    var isCancelled: Boolean? = null

    @TableField(name = "VALID_SINCE")
    var validSince: Timestamp? = null

    @TableField(name = "VALID_TO")
    var validTo: Timestamp? = null

    @TableField(name = "IS_COMPLETED")
    var isCompleted: Boolean? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasOne(model = Contract::class, fieldOnThis = "ID", fieldOnThat = "CONTRACT_STATUS_ID")
    var contract: Contract? = null

    @BelongsTo(model = Contract::class, fieldOnThis = "PARENT_CONTRACT_ID", fieldOnThat = "ID")
    var parentContract: Contract? = null

    @BelongsTo(model = Contract::class, fieldOnThat = "ID", fieldOnThis = "ROOT_CONTRACT_ID")
    var rootContract: Contract? = null

}

