package models.person

import models.persontocontactlink.PersonToContactLink
import models.persontocounterpartylink.PersonToCounterPartyLink
import org.jooq.generated.tables.People
import orm.annotations.*
import orm.persongeneratedrepository.PersonRecord
import java.sql.Timestamp

/**
 * Created by Муса on 05.12.2017.
 */
@IsModel(jooqTable = People::class)
class Person {

    val record: PersonRecord by lazy { PersonRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "NAME")
    var name: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasMany(model = PersonToContactLink::class, fieldOnThis = "ID", fieldOnThat = "PERSON_ID")
    var personToContactLinks: MutableList<PersonToContactLink>? = null

    @HasMany(model = PersonToCounterPartyLink::class, fieldOnThat = "PERSON_ID", fieldOnThis = "ID")
    var personToCounterPartyLinks: MutableList<PersonToCounterPartyLink>? = null
}