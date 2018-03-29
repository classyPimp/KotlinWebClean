package controllers.approval

import composers.approval.ApprovalComposers
import composers.approval.ofcontract.ApprovalOfContractCreateComposer
import controllers.BaseController
import models.approval.Approval
import models.approval.daos.ApprovalDaos
import models.approval.tojsonserializers.ApprovalSerializers
import models.approval.tojsonserializers.ofcontract.ApprovalOfContractShowJsonSerializer
import orm.approvalgeneratedrepository.ApprovalToJsonSerializer
import router.src.ServletRequestContext
import sun.plugin.dom.exception.InvalidStateException

class ApprovalOfContractController(context: ServletRequestContext) : BaseController(context) {

    fun create() {
        val params = requestParams()
        val contractId = routeParams().get("contractId")?.toLongOrNull()
                ?: throw InvalidStateException("no contractId supplied")
        val composer = ApprovalComposers.OfContract.create(contractId, params)
        composer.onError = {
            renderJson(
                ApprovalSerializers.OfContract.create.onError(it)
            )
        }
        composer.onSuccess = {
            renderJson(
                ApprovalSerializers.OfContract.create.onSuccess(it)
            )
        }
        composer.run()
    }

    fun show() {
        val contractId = routeParams().get("contractId")?.toLongOrNull()
        contractId ?: throw InvalidStateException("contractId param not supplied")
        val approval = ApprovalDaos.show.ofContract(contractId) as Approval?

        if (approval == null) {
            renderJson(
                ApprovalToJsonSerializer(
                    Approval().also{
                        it.record.validationManager.addGeneralError("404")
                    }
                ).also{
                    it.includeErrors()
                }.serializeToString()
            )
            return
        }

        renderJson(
                ApprovalOfContractShowJsonSerializer.onSuccess(approval)
        )
    }


}