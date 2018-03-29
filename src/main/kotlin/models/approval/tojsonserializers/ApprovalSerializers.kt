package models.approval.tojsonserializers

import models.approval.tojsonserializers.ofcontract.ApprovalOfContractCreateToJsonSerializer

object ApprovalSerializers {

    object OfContract {
        val create = ApprovalOfContractCreateToJsonSerializer
    }

}