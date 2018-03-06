package controllers.contractstatus

import composers.contractstatus.ContractStatusComposers
import controllers.BaseController
import models.contract.tojsonserializers.ContractSerializers
import models.contractstatus.ContractStatus
import models.contractstatus.daos.ContractStatusDaos
import models.contractstatus.tojsonserializers.ContractStatusSerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class ContractStatusForContractController(context: ServletRequestContext) : BaseController(context) {

    fun show() {
        val contractId = routeParams().get("contractId")?.toLongOrNull()

        if (contractId == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val contractStatus = ContractStatusDaos.show.forContract(
                contractId = contractId
        )

        if (contractStatus == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                ContractStatusSerializers.ForContract.show.onSuccess(contractStatus)
        )
    }

    fun edit() {
        val contractId = routeParams().get("contractId")?.toLongOrNull()
        if (contractId == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }
        val contractStatus = ContractStatusDaos.show.forContractEdit(
                contractId = contractId
        )
        if (contractStatus == null) {
            sendError(SC_NOT_FOUND)
            return
        }
        renderJson(
                ContractStatusSerializers.ForContract.edit.onSuccess(contractStatus)
        )
    }

    fun update() {
        val id = routeParams().get("id")?.toLongOrNull()
        val params = requestParams()
        val composer = ContractStatusComposers.forContractUpdate(id, params)
        composer.onError = {
            ContractStatusSerializers.ForContract.update.onError(it)
        }
        composer.onSuccess = {
            ContractStatusSerializers.ForContract.update.onSuccess(it)
        }
        composer.run()
    }

}