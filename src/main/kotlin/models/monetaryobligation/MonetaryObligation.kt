package models.monetaryobligation

import models.contract.Contract
import models.monetaryobligationpart.MonetaryObligationPart
import org.jooq.generated.tables.MonetaryObligations
import orm.annotations.*
import orm.monetaryobligationgeneratedrepository.MonetaryObligationRecord
import java.sql.Timestamp

@IsModel(jooqTable = MonetaryObligations::class)
class MonetaryObligation {

    val record: MonetaryObligationRecord by lazy { MonetaryObligationRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "TOTAL_AMOUNT")
    var totalAmount: Long? = null

    @TableField(name = "DESCRIPTION")
    var description: String? = null

    @TableField(name = "IS_CREDIT")
    var isCredit: Boolean? = null

    @TableField(name = "CONTRACT_ID")
    var contractId: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = Contract::class, fieldOnThat = "ID", fieldOnThis = "CONTRACT_ID")
    var contract: Contract? = null

    @HasMany(model = MonetaryObligationPart::class, fieldOnThis = "ID", fieldOnThat = "MONETARY_OBLIGATION_ID")
    var monetaryObligationParts: MutableList<MonetaryObligationPart>? = null

}

