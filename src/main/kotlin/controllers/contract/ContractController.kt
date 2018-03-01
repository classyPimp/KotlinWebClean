package controllers.contract

import composers.contract.ContractComposers
import controllers.BaseController
import controllers.contract.contracttocounterpartylink.ContractContractToCounterPartyLinkController
import controllers.contract.contracttouploadeddocumentlink.ContractContractToUploadedDocumentLinkController
import controllers.contract.manage.ContractManageController
import controllers.contract.manage.monetaryobligation.ContractMonetaryObligationController
import models.contract.daos.ContractDaos
import models.contract.tojsonserializers.ContractSerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class ContractController(context: ServletRequestContext) : BaseController(context) {

    companion object {
        fun contractToCounterPartyLink(context: ServletRequestContext): ContractContractToCounterPartyLinkController {
            return ContractContractToCounterPartyLinkController(context)
        }

        fun contractToUploadedDocumentLink(context: ServletRequestContext): ContractContractToUploadedDocumentLinkController {
            return ContractContractToUploadedDocumentLinkController(context)
        }

        fun manage(context: ServletRequestContext): ContractManageController {
            return ContractManageController(context)
        }

        val manage = ContractManageController

    }

    fun create() {
        val params = requestParams()
        val composer = ContractComposers.create(params)
        composer.onError = {
            renderJson(
                    ContractSerializers.create.onError(it)
            )
        }
        composer.onSuccess = {
            renderJson(
                    ContractSerializers.create.onSuccess(it)
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

        val contract = ContractDaos.show.forShow(id)

        if (contract == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                ContractSerializers.show.onSuccess(contract)
        )
    }

    fun index() {
        val contracts = ContractDaos.index.forIndex()

        renderJson(
                ContractSerializers.index.onSuccess(contracts)
        )
    }

}