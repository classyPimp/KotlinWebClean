package models.contact.factories

import models.contact.Contact
import models.contact.ContactRequestParametersWrapper
import models.contacttype.ContactType
import models.persontocontactlink.PersonToContactLink
import org.jooq.generated.Tables.CONTACT_TYPES
import orm.contacttypegeneratedrepository.ContactTypeRecord

object PersonCreate {

    fun create(params: ContactRequestParametersWrapper, personId: Long): Contact {
        val contact = Contact().also {
            it.contactTypeId = params.contactTypeId
            it.value = params.value
            it.personToContactLink = constructPersonToContactLink(personId)
            it.contactType = findAndSetContactType(it.contactTypeId)
        }
        return contact
    }

    private fun constructPersonToContactLink(personId: Long): PersonToContactLink {
        val personToContactLink = PersonToContactLink().also {
            it.personId = personId
        }
        return personToContactLink
    }

    private fun findAndSetContactType(contactTypeId: Long?): ContactType? {
        contactTypeId?.let {
            return ContactTypeRecord.GET().where(CONTACT_TYPES.ID.eq(contactTypeId)).limit(1).execute().firstOrNull()
        } ?: return null
    }

}