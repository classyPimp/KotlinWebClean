package models.incorporationform

import models.counterparty.CounterParty
import org.jooq.generated.tables.IncorporationForms
import orm.annotations.*
import orm.incorporationformgeneratedrepository.IncorporationFormRecord
import java.sql.Timestamp

@IsModel(jooqTable = IncorporationForms::class)
class IncorporationForm {

    val record: IncorporationFormRecord by lazy { IncorporationFormRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "NAME")
    var name: String? = null

    @TableField(name = "NAME_SHORT")
    var nameShort: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasMany(model=CounterParty::class, fieldOnThis = "ID", fieldOnThat = "INCORPORATION_FORM_ID")
    var counter_parties: MutableList<CounterParty>? = null

}

