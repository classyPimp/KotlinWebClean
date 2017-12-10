package models.contacttype.tojsonserializers

import models.contacttype.ContactType
import orm.contacttypegeneratedrepository.ContactTypeToJsonSerializer

object Create {

    fun onSuccess(contactType: ContactType): String {
        ContactTypeToJsonSerializer(contactType).let {

            return it.serializeToString()
        }
    }

    fun onError(contactType: ContactType): String {
        ContactTypeToJsonSerializer(contactType). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}