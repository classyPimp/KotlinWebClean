package controllers.contacttypes

import composers.contacttypes.ContactTypesComposers
import controllers.ApplicationControllerBase
import models.contacttype.ContactType
import models.contacttype.tojsonserializers.ContactTypeSerializers
import org.jooq.generated.tables.ContactTypes.CONTACT_TYPES
import orm.contacttypegeneratedrepository.ContactTypeRecord
import orm.contacttypegeneratedrepository.ContactTypeToJsonSerializer
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse

class ContactTypesController(context: ServletRequestContext) : ApplicationControllerBase(context) {


    fun create(){
        ContactTypesComposers.create(requestParams()).let {

            it.onSuccess = { contactType: ContactType ->
                renderJson(
                        ContactTypeSerializers.create.onSuccess(contactType)
                )
            }

            it.onError = { contactType: ContactType ->

                renderJson(
                        ContactTypeSerializers.create.onError(contactType)
                )
            }

            it.run()

        }
    }

    fun index(){
        val contactTypes = ContactTypeRecord.GET().execute()
        renderJson(
                ContactTypeSerializers.index.onSuccess(contactTypes)
        )
    }

    fun get(){
        val id: Long? = context.routeParameters["id"]?.toLongOrNull()
        var contactType: ContactType? = null

        id?.let {
            contactType = ContactTypeRecord.GET().where(CONTACT_TYPES.ID.eq(it)).execute().firstOrNull()
        }

        contactType?.let {
            renderJson(ContactTypeToJsonSerializer(it).serializeToString())
        } ?: context.response.sendError(HttpServletResponse.SC_NOT_FOUND)
    }

    fun update(){
        val id = context.routeParameters["id"]?.toLongOrNull()
        val cmpsr = ContactTypesComposers.update(requestParams(), id)

        cmpsr.onSuccess = { contactType: ContactType ->
            renderJson(
                    ContactTypeSerializers.update.onSuccess(contactType)
            )
        }

        cmpsr.onError = { contactType: ContactType ->
            renderJson(
                    ContactTypeSerializers.update.onError(contactType)
            )
        }

        cmpsr.run()
    }

    fun destroy(){
        val id = context.routeParameters["id"]?.toLongOrNull()
        val composer = ContactTypesComposers.destroy(id)

        composer.onSuccess = {contactType: ContactType ->
            renderJson(
                    ContactTypeSerializers.destroy.onSuccess(contactType)
            )
        }

        composer.onError = { contactType: ContactType ->
            renderJson(
                    ContactTypeSerializers.destroy.onError(contactType)
            )
        }

        composer.run()
    }


}