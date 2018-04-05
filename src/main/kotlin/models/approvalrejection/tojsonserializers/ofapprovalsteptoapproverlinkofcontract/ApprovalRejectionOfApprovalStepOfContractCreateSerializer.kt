package models.approvalrejection.tojsonserializers.ofapprovalsteptoapproverlinkofcontract

import models.approvalrejection.ApprovalRejection
import orm.approvalrejectiongeneratedrepository.ApprovalRejectionToJsonSerializer

object ApprovalRejectionOfApprovalStepOfContractCreateSerializer {

    fun onSuccess(approvalRejection: ApprovalRejection): String {
        ApprovalRejectionToJsonSerializer(approvalRejection).let {
            it.includeApprovalRejectionToUploadedDocumentLinks() {
                it.includeUploadedDocument()
            }
            return it.serializeToString()
        }
    }

    fun onError(approvalRejection: ApprovalRejection): String {
        ApprovalRejectionToJsonSerializer(approvalRejection). let {
            it.includeApprovalRejectionToUploadedDocumentLinks() {
                it.includeErrors()
                it.includeUploadedDocument() {
                    it.includeErrors()
                }
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}