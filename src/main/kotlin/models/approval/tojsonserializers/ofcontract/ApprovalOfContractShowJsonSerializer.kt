package models.approval.tojsonserializers.ofcontract

import models.approval.Approval
import orm.approvalgeneratedrepository.ApprovalToJsonSerializer

/**
 * Created by Муса on 29.03.2018.
 */
object ApprovalOfContractShowJsonSerializer {

    fun onSuccess(approval: Approval): String {
        return ApprovalToJsonSerializer(approval).also {
            it.includeApprovalRejections() {
                it.includeApprovalRejectionToUploadedDocumentLinks() {
                    it.includeUploadedDocument()
                }
                it.includeDiscussion()
                it.includeUser()
            }
            it.includeApprovalSteps() {
                it.includeApprovalStepToUploadedDocumentLinks() {
                    it.includeUploadedDocument()
                }
                it.includeApprovalStepToApproverLinks() {
                    it.includeUser()

                }
            }
        }.serializeToString()
    }

}