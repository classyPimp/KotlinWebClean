package controllers.contract.contracttocounterpartylink

import composers.contract.ContractComposers
import controllers.BaseController
import models.contracttocounterpartylink.tojsonserializers.ContractToCounterPartyLinkSerializers
import router.src.ServletRequestContext

class ContractContractToCounterPartyLinkController(context: ServletRequestContext) : BaseController(context) {

    fun destroy() {
        val contractId = context.routeParameters.get("contractId")?.toLongOrNull()
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = ContractComposers.ContractToCounterPartyLink.destroy(
                contractId = contractId,
                id = id
        )

        composer.onError = {
            ContractToCounterPartyLinkSerializers.ForContract.destroy.onError(it)
        }

        composer.onSuccess = {
            ContractToCounterPartyLinkSerializers.ForContract.destroy.onSuccess(it)
        }

        composer.run()
    }

    fun replace() {
        val contractId = context.routeParameters.get("contractId")?.toLongOrNull()
        val id = context.routeParameters.get("id")?.toLongOrNull()
        val counterPartyIdToReplaceWith = context.routeParameters.get("counterPartyIdToReplaceWith")?.toLongOrNull()

        val composer = ContractComposers.ContractToCounterPartyLink.replace(
                contractId = contractId,
                id = id,
                counterPartyIdToReplaceWith = counterPartyIdToReplaceWith
        )

        composer.onError = {
            renderJson(
                ContractToCounterPartyLinkSerializers.ForContract.replace.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                ContractToCounterPartyLinkSerializers.ForContract.replace.onSuccess(it)
            )
        }

        composer.run()
    }

    fun create() {
        val contractId = context.routeParameters.get("contractId")?.toLongOrNull()
        val params = requestParams()

        val composer = ContractComposers.ContractToCounterPartyLink.create(contractId, params)

        composer.onError = {
            renderJson(
                ContractToCounterPartyLinkSerializers.ForContract.create.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                ContractToCounterPartyLinkSerializers.ForContract.create.onSuccess(it)
            )
        }

        composer.run()

    }

    fun update() {
        val contractId = routeParams().get("contractId")?.toLongOrNull()
        val id = routeParams().get("id")?.toLongOrNull()
        val params = requestParams()

        val composer = ContractComposers.ContractToCounterPartyLink.update(contractId, id, params)

        composer.onError = {
            renderJson(
                    ContractToCounterPartyLinkSerializers.ForContract.update.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    ContractToCounterPartyLinkSerializers.ForContract.update.onSuccess(it)
            )
        }

        composer.run()
    }

}