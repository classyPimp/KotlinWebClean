package models.contacttype.daos

import org.jooq.generated.tables.ContactTypes
import orm.contacttypegeneratedrepository.ContactTypeRecord
import models.contacttype.ContactType
import org.jooq.generated.Tables

object ContactTypeIndexDao {

    fun getSpecificForPerson(): MutableList<ContactType> {
        return ContactTypeRecord.GET()
                .where(
                        Tables.CONTACT_TYPES.IS_SPECIFIC_FOR_TYPE.isNull
                                .or(Tables.CONTACT_TYPES.IS_SPECIFIC_FOR_TYPE.eq(ContactType.Companion.IsSpecificForTypeAllowedValues.person))
                )
                .execute()
    }

    fun getSpecificForCounterParty(): MutableList<ContactType> {
        return ContactTypeRecord.GET()
                .where(
                        Tables.CONTACT_TYPES.IS_SPECIFIC_FOR_TYPE.isNull
                                .or(Tables.CONTACT_TYPES.IS_SPECIFIC_FOR_TYPE.eq(ContactType.Companion.IsSpecificForTypeAllowedValues.counterParty))
                )
                .execute()
    }


}