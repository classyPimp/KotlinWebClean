package models.approvalsteptoapproverlink.updaters.ofcontract

import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import java.sql.Timestamp
import java.time.Instant


object ApprovalStepToApprovalLinkOfContractUpdater {

    fun whenApproved(model: ApprovalStepToApproverLink) {
        model.record.also {
            it.isApproved = Timestamp(Instant.now().toEpochMilli())
        }
    }

}