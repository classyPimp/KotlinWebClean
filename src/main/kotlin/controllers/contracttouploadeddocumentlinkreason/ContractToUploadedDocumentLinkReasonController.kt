package controllers.contracttouploadeddocumentlinkreason

import composers.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReasonComposers
import controllers.BaseController
import controllers.contracttouploadeddocumentlinkreason.formfeeds.ContractToUploadedDocumentLinkReasonFormFeedsController
import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason
import models.contracttouploadeddocumentlinkreason.daos.ContractToUploadedDocumentLinkReasonDaos
import models.contracttouploadeddocumentlinkreason.tojsonserializers.ContractToUploadedDocumentLinkReasonSerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class ContractToUploadedDocumentLinkReasonController(context: ServletRequestContext) : BaseController(context) {

    companion object {
        fun formFeeds(context: ServletRequestContext): ContractToUploadedDocumentLinkReasonFormFeedsController {
            return ContractToUploadedDocumentLinkReasonFormFeedsController(context)
        }
    }

    fun create() {
        val params = requestParams()
        val composer = ContractToUploadedDocumentLinkReasonComposers.create(params)

        composer.onError = {
            renderJson(
                    ContractToUploadedDocumentLinkReasonSerializers.create.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    ContractToUploadedDocumentLinkReasonSerializers.create.onError(it)
            )
        }

        composer.run()
    }

    fun show() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (id == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val contractToUploadedDocumentLinkReason = ContractToUploadedDocumentLinkReasonDaos.show.forShow(id!!)

        if (contractToUploadedDocumentLinkReason == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                ContractToUploadedDocumentLinkReasonSerializers.show.onSuccess(contractToUploadedDocumentLinkReason)
        )
    }

    fun index() {
        val contractToUploadedDocumentLinkReasons = ContractToUploadedDocumentLinkReasonDaos.index.forIndex()

        renderJson(
                ContractToUploadedDocumentLinkReasonSerializers.index.onSuccess(contractToUploadedDocumentLinkReasons)
        )
    }

    fun edit() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (id == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val contractToUploadedDocumentLinkReason = ContractToUploadedDocumentLinkReasonDaos.show.forEdit(id)

        if (contractToUploadedDocumentLinkReason == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                ContractToUploadedDocumentLinkReasonSerializers.edit.onSuccess(contractToUploadedDocumentLinkReason)
        )
    }

    fun update() {
        val params = requestParams()
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = ContractToUploadedDocumentLinkReasonComposers.update(params, id)

        composer.onError = {
            renderJson(
                    ContractToUploadedDocumentLinkReasonSerializers.update.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    ContractToUploadedDocumentLinkReasonSerializers.update.onError(it)
            )
        }

        composer.run()
    }

    fun destroy() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = ContractToUploadedDocumentLinkReasonComposers.destroy(id)

        composer.onError = {
            renderJson(
                    ContractToUploadedDocumentLinkReasonSerializers.destroy.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    ContractToUploadedDocumentLinkReasonSerializers.destroy.onError(it)
            )
        }

        composer.run()

    }

}