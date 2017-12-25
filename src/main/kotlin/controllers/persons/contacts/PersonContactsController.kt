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
            println("should render on success")
            renderJson(
                    ContactSerializers.personCreate.onSuccess(contact)
            )
        }

        cmpsr.onError = {
            contact: Contact ->
            val toRender = ContactSerializers.personCreate.onError(contact)
            println("should render on error: ${toRender}")
            renderJson(
                    ContactSerializers.personCreate.onError(contact)
            )
        }

        cmpsr.run()
    }

    fun delete(){
        val cmpsr = PersonsComposers.Contacts.destroy(
                context.routeParameters.get("personId")?.toLongOrNull(),
                context.routeParameters.get("id")?.toLongOrNull()
        )

        cmpsr.onSuccess = {
            contact: Contact ->
            renderJson(
                    ContactSerializers.personDestroy.onSuccess(contact)
            )
        }

        cmpsr.onError = {
            contact: Contact ->
            renderJson(
                    ContactSerializers.personDestroy.onError(contact)
            )
        }

        cmpsr.run()

    }

    fun update(){
        val composer = PersonsComposers.Contacts.update(
                requestParams(),
                context.routeParameters.get("personId")?.toLongOrNull(),
                context.routeParameters.get("id")?.toLongOrNull()
        )

        composer.onSuccess = {
            contact: Contact ->
            ContactSerializers.personUpdate.onSuccess(contact).let {
                renderJson(it)
            }
        }

        composer.onError = {
            contact: Contact ->
            ContactSerializers.personUpdate.onError(contact).let {
                renderJson(it)
            }
        }

        composer.run()
    }

}