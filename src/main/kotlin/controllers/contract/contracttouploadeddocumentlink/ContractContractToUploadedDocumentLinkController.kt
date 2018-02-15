package controllers.contract.contracttouploadeddocumentlink

import composers.contract.ContractComposers
import controllers.BaseController
import models.contracttouploadeddocumentlink.daos.ContractToUploadedDocumentLinkDaos
import models.contracttouploadeddocumentlink.tojsonserializers.ContractToUploadedDocumentLinkSerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class ContractContractToUploadedDocumentLinkController(context: ServletRequestContext) : BaseController(context) {

    fun create() {
        val contractId = routeParams().get("contractId")?.toLongOrNull()
        val params = requestParams()

        val composer = ContractComposers.ContractToUploadedDocumentLinkComposers.create(contractId, params)

        composer.onError = {
            renderJson(
                ContractToUploadedDocumentLinkSerializers.create.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                ContractToUploadedDocumentLinkSerializers.create.onSuccess(it)
            )
        }

        composer.run()
    }

    fun show() {
        val id = routeParams().get("id")?.toLongOrNull()
        if (id == null) {
            throw IllegalStateException()
        }

        val contractToUploadedDocumentLink = ContractToUploadedDocumentLinkDaos.show.forShow(id)

        if (contractToUploadedDocumentLink == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                ContractToUploadedDocumentLinkSerializers.show.onSuccess(contractToUploadedDocumentLink)
        )
    }

    fun update() {
        val contractId = routeParams().get("contractId")?.toLongOrNull()
        val id = routeParams().get("id")?.toLongOrNull()
        val params = requestParams()

        val composer = ContractComposers.ContractToUploadedDocumentLinkComposers.update(contractId, id, params)

        composer.onError = {
            renderJson(
                    ContractToUploadedDocumentLinkSerializers.update.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    ContractToUploadedDocumentLinkSerializers.update.onSuccess(it)
            )
        }

        composer.run()
    }

    fun index() {
        val contractId = routeParams().get("contractId")?.toLongOrNull()

        if (contractId == null) {
            throw IllegalStateException()
        }

        val contractToUploadedDocumentLinks = ContractToUploadedDocumentLinkDaos.index.forIndex(contractId)

        renderJson(
                ContractToUploadedDocumentLinkSerializers.index.onSuccess(contractToUploadedDocumentLinks)
        )
    }

    fun destroy() {
        val contractId = routeParams().get("contractId")?.toLongOrNull()
        val id = routeParams().get("id")?.toLongOrNull()

        val composer = ContractComposers.ContractToUploadedDocumentLinkComposers.destroy(contractId, id)

        composer.onError = {
            renderJson(
                    ContractToUploadedDocumentLinkSerializers.destroy.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    ContractToUploadedDocumentLinkSerializers.destroy.onSuccess(it)
            )
        }

        composer.run()
    }

}