package controllers.contract.manage.monetaryobligation

import composers.contract.ContractComposers
import controllers.BaseController
import models.monetaryobligation.daos.MonetaryObligationDaos
import models.monetaryobligation.tojsonserializers.MonetaryObligationSerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR

class ContractMonetaryObligationController(context: ServletRequestContext) : BaseController(context) {

    fun create() {
        val params = requestParams()
        val contractId = routeParams().get("contractId")?.toLongOrNull()

        val composer = ContractComposers.monetaryObligation.create(contractId, params)

        composer.onError = {
            renderJson(
                    MonetaryObligationSerializers.forContractCreate.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    MonetaryObligationSerializers.forContractCreate.onSuccess(it)
            )
        }

        composer.run()
    }

    fun index() {
        val contractId = routeParams().get("contractId")?.toLongOrNull()

        if (contractId == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val monetaryObligations = MonetaryObligationDaos.index.forContractManage(contractId)

        renderJson(
                MonetaryObligationSerializers.forContractManageIndex.onSuccess(monetaryObligations)
        )
    }

    fun update() {
        val id = routeParams().get("id")?.toLongOrNull()
        val params = requestParams()
        val composer = ContractComposers.monetaryObligation.update(id, params)

        composer.onError = {
            renderJson(
                MonetaryObligationSerializers.update.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                MonetaryObligationSerializers.update.onSuccess(it)
            )
        }

        composer.run()
    }

}