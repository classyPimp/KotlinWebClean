package controllers.approvalrejection

import composers.approvalrejection.ApprovalRejectionOfApprovalStepToApprovalLinkOfContractCreateComposer
import controllers.ApplicationControllerBase
import models.approvalrejection.tojsonserializers.ofapprovalsteptoapproverlinkofcontract.ApprovalRejectionOfApprovalStepOfContractCreateSerializer
import router.src.ServletRequestContext

class ApprovalRejectionOfApprovalStepToApprovalLinkOfContractController(context: ServletRequestContext) : ApplicationControllerBase(context) {

    fun create() {
        val approvalStepToApproverLinkId = routeParams().get("approvalStepToApproverLinkId")?.toLongOrNull()
        val params = requestParams()
        val composer = ApprovalRejectionOfApprovalStepToApprovalLinkOfContractCreateComposer(
                approvalStepToApproverLinkId,
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