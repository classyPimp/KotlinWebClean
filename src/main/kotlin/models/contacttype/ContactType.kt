package models.contacttype

import models.contact.Contact
import org.jooq.generated.tables.ContactTypes
import orm.annotations.HasMany
import orm.annotations.IsModel
import orm.annotations.IsPrimaryKey
import orm.annotations.TableField
import orm.contacttypegeneratedrepository.ContactTypeRecord
import java.sql.Timestamp

/**
 * Created by Муса on 06.12.2017.
 */
@IsModel(ContactTypes::class)
class ContactType {

    val record: ContactTypeRecord by lazy { ContactTypeRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @TableField(name = "NAME")
    var name: String? = null

    @TableField(name = "IS_SPECIFIC_FOR_TYPE")
    var isSpecificForType: String? = null

    @TableField(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @TableField(name = "UPDATED_AT")
    var updatedAt: Timestamp? = null

    @HasMany(model = Contact::class, fieldOnThat = "CONTACT_TYPE_ID", fieldOnThis = "ID")
    var contacts: MutableList<Contact>? = null

    companion object {
        object IsSpecificForTypeAllowedValues {
            val person = "person"
            val counterParty = "counterParty"
        }
    }

}