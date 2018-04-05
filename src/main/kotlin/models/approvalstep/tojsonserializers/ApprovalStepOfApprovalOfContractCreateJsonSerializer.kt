package models.approvalstep.tojsonserializers

import models.approvalstep.ApprovalStep
import orm.approvalstepgeneratedrepository.ApprovalStepToJsonSerializer

object ApprovalStepOfApprovalOfContractCreateJsonSerializer {

    fun onSuccess(approvalStep: ApprovalStep): String {
        ApprovalStepToJsonSerializer(approvalStep).let {

            return it.serializeToString()
        }
    }

    fun onError(approvalStep: ApprovalStep): String {
        ApprovalStepToJsonSerializer(approvalStep). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}