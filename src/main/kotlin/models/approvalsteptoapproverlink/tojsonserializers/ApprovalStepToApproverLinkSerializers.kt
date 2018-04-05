package models.approvalsteptoapproverlink.tojsonserializers

import models.approvalsteptoapproverlink.tojsonserializers.ofcontract.ApprovalStepToApprovalLinkOfContractApproveToJsonSerializer

object ApprovalStepToApproverLinkSerializers {

    object OfContract {
        val approve = ApprovalStepToApprovalLinkOfContractApproveToJsonSerializer
    }

}