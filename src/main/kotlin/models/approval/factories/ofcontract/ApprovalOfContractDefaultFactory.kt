package models.approval.factories.ofcontract

import models.approval.Approval
import models.approval.ApprovalRequestParametersWrapper
import models.approvalstep.ApprovalStep
import models.approvalstep.factories.ApprovalStepFactories
import models.approvaltoapproverlink.factories.ofcontract.ApprovalToApproverLinkOfContractDefaultFactory

object ApprovalOfContractDefaultFactory {

    fun create(params: ApprovalRequestParametersWrapper): Approval {
        return Approval().also {model ->
            model.approvableId = params.approvableId
            model.approvableType = "Contract"
            model.approvalToApproverLinks = ApprovalToApproverLinkOfContractDefaultFactory.create(params.approvalToApproverLinks)
            model.approvalSteps = params.approvalSteps?.let {
                it.mapTo(mutableListOf<ApprovalStep>()) {
                    ApprovalStepFactories.OfContract.default.create(it, model.approvalToApproverLinks)
                }
            }
        }
    }

}