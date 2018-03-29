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
            it.includeApprovalToApproverLinks() {
                it.includeUser()
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
            it.includeApprovalToApproverLinks() {
                it.includeErrors()
                it.includeUser() {
                    it.includeErrors()
                }
            }
            return it.serializeToString()
        }
    }

}