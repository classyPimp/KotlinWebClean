package controllers.counterparties.persontocounterpartylinks

import controllers.BaseController
import models.persontocounterpartylink.PersonToCounterPartyLink
import models.persontocounterpartylink.daos.PersonToCounterPartyLinkDaos
import models.persontocounterpartylink.tojsonserializers.PersonToCounterPartyLinkSerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

/**
 * Created by Муса on 04.01.2018.
 */
class CounterPartiesPersonToCounterPartyLinksController(context: ServletRequestContext)
    : BaseController(context) {

    fun show() {
        val counterPartyId = routeParams().get("counterPartyId")?.toLongOrNull()
        val id = routeParams().get("id")?.toLongOrNull()

        if (counterPartyId == null || id == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val personToCounterPartyLink = PersonToCounterPartyLinkDaos.show.forCounterParty(counterPartyId = counterPartyId, id = id)
        if (personToCounterPartyLink == null) {
            sendError(SC_NOT_FOUND)
            return
        }
        renderJson(
                PersonToCounterPartyLinkSerializers.ForCounterParties.show.onSuccess(personToCounterPartyLink)
        )
    }

    fun edit() {
        val id = routeParams().get("id")?.toLongOrNull()

        val counterPartyId = routeParams().get("counterPartyId")?.toLongOrNull()

        if (id == null || counterPartyId == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val personToCounterPartyLink = PersonToCounterPartyLinkDaos.show.forCounterPartyEdit(
                counterPartyId = counterPartyId,
                id = id
        )

        if (personToCounterPartyLink == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                PersonToCounterPartyLinkSerializers.ForCounterParties.edit.onSuccess(personToCounterPartyLink)
        )
    }


    fun index() {
        val counterPartyId = context.routeParameters.get("counterPartyId")?.toLongOrNull()
        val query = requestQueryStringParams().get("query")

        if (counterPartyId == null) {
            context.response.sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val links: MutableList<PersonToCounterPartyLink> = PersonToCounterPartyLinkDaos
                .index
                .forCounterParty(
                        counterPartyId = counterPartyId,
                        query = query
                )

        renderJson(
                PersonToCounterPartyLinkSerializers.ForCounterParties
                        .index
                        .onSuccess(links)
        )

    }

    fun indexEdit() {
        val counterPartyId = context.routeParameters.get("counterPartyId")?.toLongOrNull()
        val query = requestQueryStringParams().get("query")

        if (counterPartyId == null) {
            context.response.sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val links: MutableList<PersonToCounterPartyLink> = PersonToCounterPartyLinkDaos
                .index
                .forIndexEdit(
                        counterPartyId = counterPartyId,
                        query = query
                )

        renderJson(
                PersonToCounterPartyLinkSerializers.ForCounterParties
                        .index
                        .onSuccess(links)
        )
    }

}