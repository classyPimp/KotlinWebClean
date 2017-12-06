package models.persontocontactlink

import models.contact.Contact
import models.person.Person
import org.jooq.generated.tables.PersonToContactLinks
import orm.annotations.BelongsTo
import orm.annotations.IsModel
import orm.annotations.IsPrimaryKey
import orm.annotations.TableField
import orm.persontocontactlinkgeneratedrepository.PersonToContactLinkRecord
import java.sql.Timestamp

/**
 * Created by Муса on 06.12.2017.
 */
@IsModel(jooqTable = PersonToContactLinks::class)
class PersonToContactLink {

    val record: PersonToContactLinkRecord by lazy { PersonToContactLinkRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @TableField(name = "PERSON_ID")
    var personId: Long? = null

    @TableField(name = "CONTACT_ID")
    var contactId: Long? = null

    @BelongsTo(model = Person::class, fieldOnThis = "PERSON_ID", fieldOnThat = "ID")
    var person: Person? = null

    @BelongsTo(model = Contact::class, fieldOnThat = "ID", fieldOnThis = "CONTACT_ID")
    var contact: Contact? = null

}