package composers.persons

import composers.persons.contacts.ContactsDestroy
import composers.persons.contacts.ContactsCreate
import composers.persons.contacts.ContactsUpdate
import utils.requestparameters.IParam

/**
 * Created by Муса on 20.12.2017.
 */
object PersonsComposers {
    fun create(params: IParam): Create {
        return Create(params)
    }

    fun update(params: IParam, id: Long?): Update {
        return Update(params, id)
    }

    fun destroy(id: Long?): Destroy {
        return Destroy(id)
    }

    object Contacts {
        fun create(params: IParam, id: Long?): ContactsCreate {
            return ContactsCreate(params, id)
        }

        fun destroy(personId: Long?, id: Long?): ContactsDestroy {
            return ContactsDestroy(personId, id)
        }

        fun update(params: IParam, personId: Long?, id: Long?): ContactsUpdate {
            return ContactsUpdate(params, personId, id)
        }

    }

}