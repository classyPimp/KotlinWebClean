package models.contact

import models.contacttype.ContactType
import models.counterpartytocontactlink.CounterPartyToContactLink
import models.persontocontactlink.PersonToContactLink
import org.jooq.generated.tables.Contacts
import orm.annotations.*
import orm.contactgeneratedrepository.ContactRecord
import java.sql.Timestamp

/**
 * Created by Муса on 06.12.2017.
 */
@IsModel(jooqTable = Contacts::class)
class Contact {

    val record: ContactRecord by lazy { ContactRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CONTACT_TYPE_ID")
    var contactTypeId: Long? = null

    @TableField(name = "VALUE")
    var value: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = ContactType::class, fieldOnThis = "CONTACT_TYPE_ID", fieldOnThat = "ID")
    var contactType: ContactType? = null

    @HasOne(model = PersonToContactLink::class, fieldOnThis = "ID", fieldOnThat = "CONTACT_ID")
    var personToContactLink: PersonToContactLink? = null

    @HasOne(model = CounterPartyToContactLink::class, fieldOnThat = "CONTACT_ID", fieldOnThis = "ID")
    var counterPartyToContactLink: CounterPartyToContactLink? = null

}