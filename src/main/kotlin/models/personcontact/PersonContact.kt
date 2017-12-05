package models.personcontact

import models.person.Person
import org.jooq.generated.tables.PersonContacts
import orm.annotations.BelongsTo
import orm.annotations.IsModel
import orm.annotations.IsPrimaryKey
import orm.annotations.TableField
import orm.personcontactgeneratedrepository.PersonContactRecord
import java.sql.Timestamp

/**
 * Created by Муса on 05.12.2017.
 */
@IsModel( jooqTable = PersonContacts::class)
class PersonContact {

    val record: PersonContactRecord by lazy { PersonContactRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "PERSON_ID")
    @IsPrimaryKey
    var personId: Long? = null

    @TableField(name = "CONTACT_TYPE")
    var contact_type: Int? = null

    @TableField(name = "VALUE")
    var value: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @BelongsTo(model = Person::class, fieldOnThis = "PERSON_ID", fieldOnThat = "ID" )
    var person: Person? = null

}