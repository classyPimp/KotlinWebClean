package controllers.counterparties.contacts

import composers.counterparties.CounterPartiesComposers
import controllers.BaseController
import models.contact.daos.ContactDaos
import models.contact.tojsonserializers.ContactSerializers
import router.src.ServletRequestContext
import utils.requestparameters.IParam
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class CounterPartyContactsController(context: ServletRequestContext) : BaseController(context) {

    fun create() {
        val counterPartyId: Long? = context.routeParameters.get("counterPartyId")?.toLongOrNull()
        val params: IParam = requestParams()

        val composer = CounterPartiesComposers.Contacts.create(params, counterPartyId)

        composer.onSuccess = {
            renderJson(
                    ContactSerializers.ForCounterParties.create.onSuccess(it)
            )
        }

        composer.onError = {
            renderJson(
                    ContactSerializers.ForCounterParties.create.onError(it)
            )
        }

        composer.run()
    }

    fun show() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (id == null) {
            context.response.sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val contact = ContactDaos.show.forCounterPartyById(id)

        if (contact == null) {
            context.response.sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                ContactSerializers.ForCounterParties.show.onSuccess(contact)
        )
    }

    fun index() {
        val counterPartyId: Long? = context.routeParameters.get("counterPartyId")?.toLongOrNull()

        if (counterPartyId == null) {
            context.response.sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val contacts = ContactDaos.index.forCounterPartyByCounterPartyId(counterPartyId)

        renderJson(
                ContactSerializers.ForCounterParties.index.onSuccess(contacts)
        )
    }

    fun edit() {
        val counterPartyId = context.routeParameters.get("counterPartyId")?.toLongOrNull()
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (counterPartyId == null || id == null) {
            context.response.sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val contact = ContactDaos.edit.forCounterParty(counterPartyId, id)

        if (contact == null) {
            context.response.sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                ContactSerializers.ForCounterParties.edit.onSuccess(contact)
        )

    }

    fun update() {
        val counterPartyId = context.routeParameters.get("counterPartyId")?.toLongOrNull()
        val id = context.routeParameters.get("id")?.toLongOrNull()
        val params = requestParams()

        val composer = CounterPartiesComposers.Contacts.update(params, counterPartyId, id)

        composer.onSuccess = {
            renderJson(
                    ContactSerializers.ForCounterParties.update.onSuccess(it)
            )
        }

        composer.onError = {
            renderJson(
                    ContactSerializers.ForCounterParties.update.onError(it)
            )
        }

        composer.run()
    }

    fun destroy() {
        val counterPartyId = context.routeParameters.get("counterPartyId")?.toLongOrNull()
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = CounterPartiesComposers.Contacts.destroy(counterPartyId, id)

        composer.onSuccess = {
            renderJson(
                    ContactSerializers.ForCounterParties.destroy.onSuccess(it)
            )
        }

        composer.onError = {
            renderJson(
                    ContactSerializers.ForCounterParties.destroy.onError(it)
            )
        }

        composer.run()
    }

}