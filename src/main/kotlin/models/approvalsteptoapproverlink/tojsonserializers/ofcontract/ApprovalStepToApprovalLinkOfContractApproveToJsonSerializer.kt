package models.approvalsteptoapproverlink.tojsonserializers.ofcontract

import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import orm.approvalsteptoapproverlinkgeneratedrepository.ApprovalStepToApproverLinkToJsonSerializer

object ApprovalStepToApprovalLinkOfContractApproveToJsonSerializer {

    fun onSuccess(approvalStepToApproverLink: ApprovalStepToApproverLink): String {
        ApprovalStepToApproverLinkToJsonSerializer(approvalStepToApproverLink).let {

            return it.serializeToString()
        }
    }

    fun onError(approvalStepToApproverLink: ApprovalStepToApproverLink): String {
        ApprovalStepToApproverLinkToJsonSerializer(approvalStepToApproverLink). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}