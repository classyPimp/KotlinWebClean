package models.approval.tojsonserializers.ofcontract

import models.approval.Approval
import orm.approvalgeneratedrepository.ApprovalToJsonSerializer

object ApprovalOfContractCreateToJsonSerializer {

    fun onSuccess(approval: Approval): String {
        ApprovalToJsonSerializer(approval).let {
            it.includeApprovalSteps() {
                it.includeApprovalStepToApproverLinks() {

                }
                it.includeApprovalStepToUploadedDocumentLinks() {
                    it.includeUploadedDocument()
                }
            }
            return it.serializeToString()
        }
    }

    fun onError(approval: Approval): String {
        ApprovalToJsonSerializer(approval).let {
            it.includeErrors()
            it.includeApprovalSteps() {
                it.includeErrors()
                it.includeApprovalStepToApproverLinks() {
                    it.includeErrors()
                }
                it.includeApprovalStepToUploadedDocumentLinks() {
                    it.includeErrors()
                    it.includeUploadedDocument() {
                        it.includeErrors()
                    }
                }
            }
            return it.serializeToString()
        }
    }

}