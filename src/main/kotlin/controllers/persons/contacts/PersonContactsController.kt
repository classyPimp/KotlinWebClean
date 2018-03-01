package controllers.persons.contacts

import composers.persons.PersonsComposers
import controllers.BaseController
import models.contact.Contact
import models.contact.tojsonserializers.ContactSerializers
import org.jooq.generated.Tables.CONTACTS
import org.jooq.generated.Tables.PERSON_TO_CONTACT_LINKS
import orm.contactgeneratedrepository.ContactRecord
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR

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

    fun index() {
        val personId = routeParams().get("personId")?.toLongOrNull()
        if (personId == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }
        val contacts = ContactRecord.GET()
                .join {
                    it.personToContactLink()
                }
                .preload {
                    it.contactType()
                }
                .where(PERSON_TO_CONTACT_LINKS.PERSON_ID.eq(personId))
                .execute()

        renderJson(
                ContactSerializers.forPersonsIndexEdit.onSuccess(contacts)
        )
    }

    fun indexForEdit() {
        val personId = routeParams().get("personId")?.toLongOrNull()
        if (personId == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }
        val contacts = ContactRecord.GET()
                .join {
                    it.personToContactLink()
                }
                .preload {
                    it.contactType()
                }
                .where(PERSON_TO_CONTACT_LINKS.PERSON_ID.eq(personId))
                .execute()

        renderJson(
                ContactSerializers.forPersonsIndexEdit.onSuccess(contacts)
        )
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