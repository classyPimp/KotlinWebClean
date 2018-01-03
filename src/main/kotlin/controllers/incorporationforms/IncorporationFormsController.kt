package controllers.incorporationforms

import composers.incorporationforms.IncorporationFormsComposers
import composers.incorporationforms.formfeeds.FormFeedsController
import controllers.BaseController
import models.incorporationform.IncorporationForm
import models.incorporationform.daos.IncorporationFormDaos
import models.incorporationform.tojsonserializers.IncorporationFormSerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.*

/**
 * Created by Муса on 29.12.2017.
 */
class IncorporationFormsController(context: ServletRequestContext) : BaseController(context) {

    companion object {
        fun formFeeds(context: ServletRequestContext): FormFeedsController {
            return FormFeedsController(context)
        }
    }

    fun create() {
        val composer = IncorporationFormsComposers.create(requestParams())

        composer.onSuccess = { incorporationForm: IncorporationForm ->
            renderJson(
                    IncorporationFormSerializers.create.onSuccess(incorporationForm)
            )
        }

        composer.onError = { incorporationForm: IncorporationForm ->
            renderJson(
                    IncorporationFormSerializers.create.onError(incorporationForm)
            )
        }

        composer.run()
    }

    fun show() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (id == null) {
            context.response.sendError(SC_EXPECTATION_FAILED)
            return
        }

        val incorporationForm: IncorporationForm? = IncorporationFormDaos.show.findForShowById(id)

        if (incorporationForm == null) {
            context.response.sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                IncorporationFormSerializers.show.onSuccess(incorporationForm)
        )

    }

    fun index() {

        val incorporationForms: MutableList<IncorporationForm> = IncorporationFormDaos.index.defaultIndex()

        renderJson(
                IncorporationFormSerializers.index.onSuccess(incorporationForms)
        )
    }

    fun edit() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (id == null) {
            context.response.sendError(SC_EXPECTATION_FAILED)
            return
        }

        val incorporationForm: IncorporationForm? = IncorporationFormDaos.edit.defaultForEdit(id)

        if (incorporationForm == null) {
            context.response.sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                IncorporationFormSerializers.edit.onSuccess(incorporationForm)
        )
    }

    fun update() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = IncorporationFormsComposers.update(requestParams(), id)

        composer.onSuccess = { incorporationForm: IncorporationForm ->
            renderJson(
                    IncorporationFormSerializers.update.onSuccess(incorporationForm)
            )
        }

        composer.onError = { incorporationForm: IncorporationForm ->
            renderJson(
                    IncorporationFormSerializers.update.onError(incorporationForm)
            )
        }

        composer.run()
    }


    fun destroy() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = IncorporationFormsComposers.destroy(id)

        composer.onSuccess = { incorporationForm: IncorporationForm ->
            renderJson(
                    IncorporationFormSerializers.destroy.onSuccess(incorporationForm)
            )
        }

        composer.onError = { incorporationForm: IncorporationForm ->
            renderJson(
                IncorporationFormSerializers.destroy.onError(incorporationForm)
            )
        }

        composer.run()

    }




}