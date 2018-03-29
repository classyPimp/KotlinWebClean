package models.approval.daos

import org.jooq.generated.tables.Approvals
import orm.approvalgeneratedrepository.ApprovalRecord
import models.approval.Approval
import org.jooq.generated.Tables.APPROVALS

object ApprovalShowDao {

    val table = APPROVALS

    fun ofContract(contractId: Long): Approval? {
        val approval = ApprovalRecord.GET()
                .where(table.APPROVABLE_ID.eq(contractId).and(table.APPROVABLE_TYPE.eq("Contract")))
                .preload {
                    it.approvalSteps() {
                        it.preload {
                            it.approvalStepToApproverLinks() {
                                it.preload {
                                    it.user()
                                    it.approvalRejection() {
                                        it.preload {
                                            it.approvalRejectionToUploadedDocumentLinks() {
                                                it.preload {
                                                    it.uploadedDocument()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            it.approvalStepToUploadedDocumentLinks() {
                                it.preload {
                                    it.uploadedDocument()
                                }
                            }
                        }
                    }
                    it.approvalToApproverLinks() {
                        it.preload {
                            it.user()
                        }
                    }
                }
                .limit(1)
                .execute()
                .firstOrNull()

        return approval
    }

}