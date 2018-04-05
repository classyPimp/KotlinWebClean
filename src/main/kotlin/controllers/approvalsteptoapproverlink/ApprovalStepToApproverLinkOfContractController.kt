package controllers.approvalsteptoapproverlink

import composers.approvalsteptoapproverlink.ofcontract.ApprovalStepToApproverLinkOfContractApproveComposer
import controllers.ApplicationControllerBase
import models.approvalsteptoapproverlink.tojsonserializers.ApprovalStepToApproverLinkSerializers
import orm.approvalsteptoapproverlinkgeneratedrepository.ApprovalStepToApproverLinkToJsonSerializer
import router.src.ServletRequestContext

class ApprovalStepToApproverLinkOfContractController(context: ServletRequestContext) : ApplicationControllerBase(context) {

    fun approve() {
        val approvalStepToApproverLinkId = routeParams().get("approvalStepToApproverLinkId")?.toLongOrNull()
        val composer = ApprovalStepToApproverLinkOfContractApproveComposer(approvalStepToApproverLinkId, currentUser)
        composer.onError = {
            ApprovalStepToApproverLinkSerializers.OfContract.approve.onError(it)
        }
        composer.onSuccess = {
            ApprovalStepToApproverLinkSerializers.OfContract.approve.onSuccess(it)
        }
        composer.run()
    }

}