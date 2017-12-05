package models.counterpartycontacts

import models.counterparty.CounterParty
import org.jooq.generated.tables.CounterPartyContacts
import orm.annotations.BelongsTo
import orm.annotations.IsModel
import orm.annotations.IsPrimaryKey
import orm.annotations.TableField
import orm.counterpartycontactgeneratedrepository.CounterPartyContactRecord
import java.sql.Timestamp

/**
 * Created by Муса on 05.12.2017.
 */
@IsModel(jooqTable = CounterPartyContacts::class)
class CounterPartyContact {

    val record: CounterPartyContactRecord by lazy { CounterPartyContactRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CONTACT_TYPE")
    var contactType: Int? = null

    @TableField(name = "VALUE")
    var value: String? = null

    @TableField(name = "COUNTER_PARTY_ID")
    var counterPartyId: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = CounterParty::class, fieldOnThis = "COUNTER_PARTY_ID", fieldOnThat = "ID")
    var counterParty: CounterParty? = null

}