package models.approvalsteptoapproverlink.updaters

import models.approvalsteptoapproverlink.updaters.ofcontract.ApprovalStepToApprovalLinkOfContractUpdater


object ApprovalStepToApproverLinkUpdaters {

    object OfContract {
        val default = ApprovalStepToApprovalLinkOfContractUpdater
    }

}