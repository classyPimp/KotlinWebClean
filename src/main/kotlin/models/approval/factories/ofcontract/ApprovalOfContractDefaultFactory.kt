package models.approval.factories.ofcontract

import models.approval.Approval
import models.approval.ApprovalRequestParametersWrapper
import models.approvalstep.ApprovalStep
import models.approvalstep.factories.ApprovalStepFactories

object ApprovalOfContractDefaultFactory {

    fun create(params: ApprovalRequestParametersWrapper, contractId: Long): Approval {
        return Approval().also {model ->
            model.approvableId = contractId
            model.approvableType = "Contract"
            model.approvalSteps = params.approvalSteps?.firstOrNull()?.let {
                   mutableListOf(ApprovalStepFactories.OfContract.default.create(it))
            }
        }
    }

}