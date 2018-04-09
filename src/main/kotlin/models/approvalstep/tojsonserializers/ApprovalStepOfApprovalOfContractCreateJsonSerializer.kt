package models.approvalstep.tojsonserializers

import models.approvalstep.ApprovalStep
import orm.approvalstepgeneratedrepository.ApprovalStepToJsonSerializer

object ApprovalStepOfApprovalOfContractCreateJsonSerializer {

    fun onSuccess(approvalStep: ApprovalStep): String {
        ApprovalStepToJsonSerializer(approvalStep).let {
            it.includeApprovalStepToUploadedDocumentLinks()
            return it.serializeToString()
        }
    }

    fun onError(approvalStep: ApprovalStep): String {
        ApprovalStepToJsonSerializer(approvalStep). let {
            it.includeApprovalStepToUploadedDocumentLinks() {
                it.includeErrors()
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}