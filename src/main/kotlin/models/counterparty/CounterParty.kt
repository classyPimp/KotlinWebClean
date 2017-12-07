package models.counterparty

import models.counterpartytocontactlink.CounterPartyToContactLink
import models.incorporationform.IncorporationForm
import models.persontocounterpartylink.PersonToCounterPartyLink
import org.jooq.generated.tables.CounterParties
import orm.annotations.*
import orm.counterpartygeneratedrepository.CounterPartyRecord
import java.sql.Timestamp

/**
 * Created by Муса on 05.12.2017.
 */
@IsModel(jooqTable = CounterParties::class)
class CounterParty {

    val record: CounterPartyRecord by lazy { CounterPartyRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "INCORPORATION_FORM_ID")
    var incorporationFormId: Long? = null

    @TableField(name = "NAME")
    var name: String? = null

    @TableField(name = "NAME_SHORT")
    var nameShort: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = IncorporationForm::class, fieldOnThis = "INCORPORATION_FORM_ID", fieldOnThat = "ID")
    var incorporationForm: IncorporationForm? = null

    @HasMany(model = CounterPartyToContactLink::class, fieldOnThat = "COUNTER_PARTY_ID", fieldOnThis = "ID")
    var counterPartyContacts: MutableList<CounterPartyToContactLink>? = null

    @HasMany(model = PersonToCounterPartyLink::class , fieldOnThis = "ID", fieldOnThat = "COUNTER_PARTY_ID")
    var personToCounterPartyLinks: MutableList<PersonToCounterPartyLink>? = null

}