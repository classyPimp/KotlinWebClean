package controllers.persons.contacts

import composers.persons.PersonsComposers
import controllers.BaseController
import models.contact.Contact
import models.contact.tojsonserializers.ContactSerializers
import router.src.ServletRequestContext

/**
 * Created by Муса on 21.12.2017.
 */
class PersonContactsController(context: ServletRequestContext) : BaseController(context) {

    fun create(){
        val cmpsr = PersonsComposers.Contacts.create(requestParams(), context.routeParameters.get("personId")?.toLongOrNull())

        cmpsr.onSuccess = {
            contact: Contact ->
            renderJson(
                    ContactSerializers.personCreate.onSuccess(contact)
            )
        }

        cmpsr.onError = {
            contact: Contact ->
            renderJson(
                    ContactSerializers.personCreate.onError(contact)
            )
        }

        cmpsr.run()
    }

}