package models.approval.daos

import org.jooq.generated.tables.Approvals
import orm.approvalgeneratedrepository.ApprovalRecord
import models.approval.Approval
import org.jooq.generated.Tables.*

object ApprovalShowDao {

    val table = APPROVALS

    fun ofContract(contractId: Long): Approval? {
        val approval = ApprovalRecord.GET()
                .where(table.APPROVABLE_ID.eq(contractId).and(table.APPROVABLE_TYPE.eq("Contract")))
                .preload {
                    it.approvalRejections() {
                        it.preload {
                            it.approvalRejectionToUploadedDocumentLinks() {
                                it.preload {
                                    it.uploadedDocument()
                                }
                            }
                            it.user()
                            it.discussion()
                        }
                    }
                    it.approvalSteps() {
                        it.preload {
                            it.approvalStepToApproverLinks() {
                                it.preload {
                                    it.user()
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

    fun ofContractForApprovalStepCreate(id: Long): Approval? {
        val approval = ApprovalRecord.GET()
                .where(table.ID.eq(id))
                .preload {
                    it.approvalToApproverLinks()
                    it.approvalRejections() {
                        it.where(APPROVAL_REJECTIONS.FULFILLED.isNull)
                        it.preload {
                            it.approvalRejectionToUploadedDocumentLinks()
                            it.discussion()
                        }
                    }
                }
                .limit(1)
                .execute()
                .firstOrNull()

        if (approval != null) {
            approval.record.loadApprovalSteps {
                it.where(APPROVAL_STEPS.ID.eq(approval.lastStageId!!)).preload {
                    it.approvalStepToApproverLinks()
                }
            }
        }
        return approval
    }

    fun findForApprovalRejectionOfContractCreate(approvalId: Long): Approval? {
        return ApprovalRecord.GET()
                .where(table.ID.eq(approvalId))
                .limit(1)
                .execute()
                .firstOrNull()
    }

}