package controllers.contract.manage

import controllers.BaseController
import models.contract.daos.ContractDaos
import models.contract.tojsonserializers.ContractSerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class ContractManageController(context: ServletRequestContext) : BaseController(context) {

    fun edit() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (id == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val contract = ContractDaos.show.forManageEdit(id)

        if (contract == null) {
            sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                ContractSerializers.Manage.edit.onSuccess(contract)
        )
    }

}