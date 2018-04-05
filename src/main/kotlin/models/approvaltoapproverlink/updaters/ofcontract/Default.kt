package models.approvaltoapproverlink.updaters.ofcontract

import models.approvaltoapproverlink.ApprovalToApproverLink
import java.sql.Timestamp
import java.time.Instant


object Default {

    fun whenApproved(model: ApprovalToApproverLink) {
        model.record.also {
            it.isApproved = Timestamp(Instant.now().toEpochMilli())
        }
    }

}