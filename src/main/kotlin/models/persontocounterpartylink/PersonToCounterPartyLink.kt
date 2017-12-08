package models.persontocounterpartylink

import models.counterparty.CounterParty
import models.person.Person
import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReason
import org.jooq.generated.tables.PersonToCounterPartyLinkReasons
import org.jooq.generated.tables.PersonToCounterPartyLinks
import orm.annotations.BelongsTo
import orm.annotations.IsModel
import orm.annotations.IsPrimaryKey
import orm.annotations.TableField
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkRecord
import java.sql.Timestamp

/**
 * Created by Муса on 05.12.2017.
 */
@IsModel(jooqTable = PersonToCounterPartyLinks::class)
class PersonToCounterPartyLink {

    val record: PersonToCounterPartyLinkRecord by lazy { PersonToCounterPartyLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "PERSON_ID")
    var personId: Long? = null

    @TableField(name = "COUNTER_PARTY_ID")
    var counterPartyId: Long? = null

    @TableField(name = "PERSON_TO_COUNTER_PARTY_LINK_REASON_ID")
    var personToCounterPartyLinkReasonId: Long? = null

    @TableField(name = "SPECIFIC_DETAILS")
    var specificDetails: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = Person::class, fieldOnThis = "PERSON_ID", fieldOnThat = "ID")
    var person: Person? = null

    @BelongsTo(model = CounterParty::class, fieldOnThis = "COUNTER_PARTY_ID", fieldOnThat = "ID")
    var counterParty: CounterParty? = null

    @BelongsTo(model = PersonToCounterPartyLinkReason::class, fieldOnThis = "PERSON_TO_COUNTER_PARTY_LINK_REASON_ID", fieldOnThat = "ID")
    var personToCounterPartyLinkReason: PersonToCounterPartyLinkReason? = null

}