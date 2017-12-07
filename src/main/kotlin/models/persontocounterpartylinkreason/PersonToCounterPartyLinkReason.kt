package models.persontocounterpartylinkreason

import models.persontocounterpartylink.PersonToCounterPartyLink
import org.jooq.generated.tables.PersonToCounterPartyLinkReasons
import orm.annotations.HasMany
import orm.annotations.IsModel
import orm.annotations.IsPrimaryKey
import orm.annotations.TableField
import orm.persontocounterpartylinkreasongeneratedrepository.PersonToCounterPartyLinkReasonRecord
import java.sql.Timestamp

/**
 * Created by Муса on 06.12.2017.
 */
@IsModel(jooqTable = PersonToCounterPartyLinkReasons::class)
class PersonToCounterPartyLinkReason {

    val record: PersonToCounterPartyLinkReasonRecord by lazy { PersonToCounterPartyLinkReasonRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "REASON_NAME")
    var reasonName: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasMany(model = PersonToCounterPartyLink::class, fieldOnThat = "PERSON_TO_COUNTER_PARTY_LINK_REASON_ID", fieldOnThis = "ID")
    var personToCounterPartyLinks: MutableList<PersonToCounterPartyLink>? = null

}