package models.counterpartytocontactlink

import models.contact.Contact
import models.counterparty.CounterParty
import org.jooq.generated.tables.CounterPartyToContactLinks
import orm.annotations.BelongsTo
import orm.annotations.IsModel
import orm.annotations.IsPrimaryKey
import orm.annotations.TableField
import orm.counterpartytocontactlinkgeneratedrepository.CounterPartyToContactLinkRecord
import java.sql.Timestamp

/**
 * Created by Муса on 06.12.2017.
 */
@IsModel(jooqTable = CounterPartyToContactLinks::class)
class CounterPartyToContactLink {

    val record: CounterPartyToContactLinkRecord by lazy { CounterPartyToContactLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "COUNTER_PARTY_ID")
    var counterPartyId: Long? = null

    @TableField(name = "CONTACT_ID")
    var contactId: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = Contact::class, fieldOnThat = "ID", fieldOnThis = "CONTACT_ID")
    var contact: Contact? = null

    @BelongsTo(model = CounterParty::class, fieldOnThis = "COUNTER_PARTY_ID", fieldOnThat = "ID")
    var counterParty: CounterParty? = null

}