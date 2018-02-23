package controllers.contract.monetaryobligation

import composers.contract.ContractComposers
import controllers.BaseController
import models.monetaryobligation.MonetaryObligation
import models.monetaryobligation.tojsonserializers.MonetaryObligationSerializers
import router.src.ServletRequestContext

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

}