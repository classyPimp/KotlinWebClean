package models.monetaryobligationpart

import models.monetaryobligation.MonetaryObligation
import org.jooq.generated.tables.MonetaryObligationParts
import orm.annotations.*
import orm.monetaryobligationpartgeneratedrepository.MonetaryObligationPartRecord
import java.sql.Timestamp

@IsModel(jooqTable = MonetaryObligationParts::class)
class MonetaryObligationPart {

    val record: MonetaryObligationPartRecord by lazy { MonetaryObligationPartRecord(this) }

    var markedForDestruction: Boolean? = null

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "AMOUNT")
    var amount: Long? = null

    @TableField(name = "MONETARY_OBLIGATION_ID")
    var monetaryObligationId: Long? = null

    @TableField(name = "DUE_DATE")
    var dueDate: Timestamp? = null

    @TableField(name = "FULFILLED_AMOUNT")
    var fulfilledAmount: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = MonetaryObligation::class, fieldOnThis = "MONETARY_OBLIGATION_ID", fieldOnThat = "ID")
    var monetaryObligation: MonetaryObligation? = null

}

