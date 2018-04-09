package controllers.approvalrejection

import composers.approvalrejection.ApprovalRejectionOfApprovalStepToApprovalLinkOfContractCreateComposer
import controllers.ApplicationControllerBase
import models.approvalrejection.tojsonserializers.ofapprovalsteptoapproverlinkofcontract.ApprovalRejectionOfApprovalStepOfContractCreateSerializer
import router.src.ServletRequestContext

class ApprovalRejectionOfApprovalOfContractController(context: ServletRequestContext) : ApplicationControllerBase(context) {

    fun create() {
        val approvalId = routeParams().get("approvalId")?.toLongOrNull()
        val params = requestParams()
        val composer = ApprovalRejectionOfApprovalStepToApprovalLinkOfContractCreateComposer(
                approvalId,
                params,
                currentUser
        )
        composer.onError = {
            renderJson (
                ApprovalRejectionOfApprovalStepOfContractCreateSerializer.onError(it)
            )
        }
        composer.onSuccess = {
            renderJson(
                ApprovalRejectionOfApprovalStepOfContractCreateSerializer.onSuccess(it)
            )
        }
        composer.run()
    }

}