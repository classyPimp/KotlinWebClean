package models.approvalstep

import models.approvalstep.ApprovalStep
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import models.approvalsteptoapproverlink.ApprovalStepToApproverLinkRequestParametersWrapper
import models.approvalsteptouploadeddocumentlink.ApprovalStepToUploadedDocumentLinkRequestParametersWrapper
import org.jooq.generated.tables.ApprovalStepToUploadedDocumentLinks
import utils.requestparameters.IParam

class ApprovalStepRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long() }
    val approvalId: Long? by lazy { requestParameters.get("approvalId")?.long() }
    val approvalStepToApproverLinks: MutableList<ApprovalStepToApproverLinkRequestParametersWrapper>? by lazy {
        requestParameters.get("approvalStepToApproverLinks")?.paramList()?.mapTo(
                mutableListOf<ApprovalStepToApproverLinkRequestParametersWrapper>()
        ) {
            ApprovalStepToApproverLinkRequestParametersWrapper(it)
        }
    }
    val approvalStepToUploadedDocumentLinks: MutableList<ApprovalStepToUploadedDocumentLinkRequestParametersWrapper>? by lazy {
        requestParameters.get("approvalStepToUploadedDocumentLinks")?.paramList()?.mapTo(
                mutableListOf<ApprovalStepToUploadedDocumentLinkRequestParametersWrapper>()
        ) {
            ApprovalStepToUploadedDocumentLinkRequestParametersWrapper(it)
        }
    }

}