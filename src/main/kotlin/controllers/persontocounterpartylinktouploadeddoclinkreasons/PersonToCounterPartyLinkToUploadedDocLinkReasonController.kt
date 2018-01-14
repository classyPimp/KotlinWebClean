package controllers.persontocounterpartylinktouploadeddoclinkreasons

import composers.persontocounterpartylinktouploadeddoclinkreasons.PersonToCounterPartyLinkToUploadedDocLinkReasonComposer
import controllers.BaseController
import controllers.persontocounterpartylinkreasons.formfeeds.FormFeedsController
import models.persontocounterpartylinktouploadeddoclinkreason.daos.PersonToCounterPartyLinkToUploadedDocLinkReasonDaos
import models.persontocounterpartylinktouploadeddoclinkreason.tojsonserializers.PersonToCounterPartyLinkToUploadedDocLinkReasonSerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class PersonToCounterPartyLinkToUploadedDocLinkReasonController(context: ServletRequestContext) : BaseController(context) {

    companion object {
        fun formFeeds(context: ServletRequestContext): FormFeedsController {
            return FormFeedsController(context)
        }
    }

    fun create(){
        val params = requestParams()
        val composer = PersonToCounterPartyLinkToUploadedDocLinkReasonComposer.create(params)

        composer.onSuccess = {
            renderJson(
                    PersonToCounterPartyLinkToUploadedDocLinkReasonSerializers.create.onSuccess(it)
            )
        }

        composer.onError = {
            renderJson(
                    PersonToCounterPartyLinkToUploadedDocLinkReasonSerializers.create.onError(it)
            )
        }

        composer.run()
    }

    fun index() {
        val linkReasons = PersonToCounterPartyLinkToUploadedDocLinkReasonDaos.index.default()

        renderJson(
                PersonToCounterPartyLinkToUploadedDocLinkReasonSerializers.index.onSuccess(linkReasons)
        )
    }

    fun show(){
        val id = routeParams().get("id")?.toLongOrNull()

        if (id == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val linkReason = PersonToCounterPartyLinkToUploadedDocLinkReasonDaos.show.byId(id)

        if (linkReason == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                PersonToCounterPartyLinkToUploadedDocLinkReasonSerializers.show.onSuccess(linkReason)
        )
    }

    fun edit() {
        val id = getIdRouteParam()

        if (id == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val linkReason = PersonToCounterPartyLinkToUploadedDocLinkReasonDaos.edit.byId(id)

        if (linkReason == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                PersonToCounterPartyLinkToUploadedDocLinkReasonSerializers.edit.onSuccess(linkReason)
        )
    }

    fun update() {
        val id = getIdRouteParam()
        val params = requestParams()

        val composer = PersonToCounterPartyLinkToUploadedDocLinkReasonComposer.update(params, id)

        composer.onSuccess = {
            renderJson(
                    PersonToCounterPartyLinkToUploadedDocLinkReasonSerializers.update.onSuccess(it)
            )
        }

        composer.onError = {
            renderJson(
                    PersonToCounterPartyLinkToUploadedDocLinkReasonSerializers.update.onError(it)
            )
        }

        composer.run()

    }

    fun destroy() {
        val id = getIdRouteParam()

        val composer = PersonToCounterPartyLinkToUploadedDocLinkReasonComposer.destroy(id)

        composer.onSuccess = {
            renderJson(
                    PersonToCounterPartyLinkToUploadedDocLinkReasonSerializers.destroy.onSuccess(it)
            )
        }

        composer.onError = {
            renderJson(
                    PersonToCounterPartyLinkToUploadedDocLinkReasonSerializers.destroy.onError(it)
            )
        }

        composer.run()
    }

    private fun getIdRouteParam(): Long? {
        return routeParams().get("id")?.toLongOrNull()
    }


}