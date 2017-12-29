package controllers.persontocounterpartylinkreasons

import composers.persontocounterpartylinkreasons.PersonToCounterPartyLinkReasonsComposers
import controllers.BaseController
import models.persontocounterpartylinkreason.PersonToCounterPartyLinkReason
import models.persontocounterpartylinkreason.daos.PersonToCounterPartyLinkReasonDaos
import models.persontocounterpartylinkreason.tojsonserializers.PersonToCounterPartyLinkReasonSerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

/**
 * Created by Муса on 28.12.2017.
 */
class PersonToCounterPartyLinkReasonController(context: ServletRequestContext) : BaseController(context) {

    fun create(){
        val composer = PersonToCounterPartyLinkReasonsComposers.create(requestParams())

        composer.onSuccess = { linkReason: PersonToCounterPartyLinkReason ->
            renderJson(
                    PersonToCounterPartyLinkReasonSerializers.create.onSuccess(linkReason)
            )
        }

        composer.onError = { linkReason: PersonToCounterPartyLinkReason ->
            renderJson(
                    PersonToCounterPartyLinkReasonSerializers.create.onError(linkReason)
            )
        }

        composer.run()
    }

    fun index(){
        val linkReasons = PersonToCounterPartyLinkReasonDaos.index.indexAll()

        renderJson(
            PersonToCounterPartyLinkReasonSerializers.index.onSuccess(linkReasons)
        )
    }

    fun show(){
        val id = context.routeParameters.get("id")?.toLongOrNull()
        if (id == null) {
            context.response.sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val linkReason = PersonToCounterPartyLinkReasonDaos.show.findById(id)

        linkReason?.let {
            renderJson(
                    PersonToCounterPartyLinkReasonSerializers.show.onSuccess(linkReason)
            )
        } ?: context.response.sendError(SC_NOT_FOUND)
    }

    fun edit() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (id == null) {
            context.response.sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val linkReason = PersonToCounterPartyLinkReasonDaos.show.findById(id)

        linkReason?.let {
            renderJson(
                    PersonToCounterPartyLinkReasonSerializers.show.onSuccess(linkReason)
            )
        } ?: context.response.sendError(SC_NOT_FOUND)
    }

    fun update() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = PersonToCounterPartyLinkReasonsComposers.update(requestParams(), id)

        composer.onSuccess = { linkReason: PersonToCounterPartyLinkReason ->
            renderJson(
                    PersonToCounterPartyLinkReasonSerializers.update.onSuccess(linkReason)
            )
        }

        composer.onError = { linkReason: PersonToCounterPartyLinkReason ->
            renderJson(
                    PersonToCounterPartyLinkReasonSerializers.update.onError(linkReason)
            )
        }

        composer.run()
    }

    fun destroy() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = PersonToCounterPartyLinkReasonsComposers.destroy(id)

        composer.onSuccess = { linkReason: PersonToCounterPartyLinkReason ->
            renderJson(
                    PersonToCounterPartyLinkReasonSerializers.destroy.onSuccess(linkReason)
            )
        }

        composer.onNotFound = {
            context.response.sendError(SC_NOT_FOUND)
        }

        composer.run()
    }

}