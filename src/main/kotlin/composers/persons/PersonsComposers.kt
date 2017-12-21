package composers.persons

import composers.persons.contacts.ContactsCreate
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
    }

}