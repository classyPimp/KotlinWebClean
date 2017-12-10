package models.contacttype.tojsonserializers

import models.contacttype.ContactType
import orm.contacttypegeneratedrepository.ContactTypeToJsonSerializer

object Index {

    fun onSuccess(contactTypes: MutableList<ContactType>): String {
        return ContactTypeToJsonSerializer.serialize(contactTypes).toString()
    }

}