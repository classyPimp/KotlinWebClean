package controllers.contracttocounterpartylink

import controllers.BaseController
import models.contracttocounterpartylink.daos.ContractToCounterPartyLinkDaos
import models.contracttocounterpartylink.tojsonserializers.ContractToCounterPartyLinkSerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class ContractToCounterPartyLinkForContractController(context: ServletRequestContext) : BaseController(context) {

    fun index() {
        val contractId = routeParams().get("contractId")?.toLongOrNull()

        if (contractId == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val contractToCounterPartyLinks = ContractToCounterPartyLinkDaos.index.forContract(
                contractId = contractId
        )

        renderJson(
                ContractToCounterPartyLinkSerializers.ForContract.index.onSuccess(contractToCounterPartyLinks)
        )
    }

    fun indexEdit() {
        val contractId = routeParams().get("contractId")?.toLongOrNull()

        if (contractId == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val contractToCounterPartyLinks = ContractToCounterPartyLinkDaos.index.forContract(
                contractId = contractId
        )

        renderJson(
                ContractToCounterPartyLinkSerializers.ForContract.index.onSuccess(contractToCounterPartyLinks)
        )
    }

}