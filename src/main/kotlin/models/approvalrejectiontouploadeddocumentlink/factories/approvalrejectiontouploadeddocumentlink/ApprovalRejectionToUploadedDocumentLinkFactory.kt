package models.approvalrejectiontouploadeddocumentlink.factories.approvalrejectiontouploadeddocumentlink

import models.approvalrejectiontouploadeddocumentlink.ApprovalRejectionToUploadedDocumentLink
import models.approvalrejectiontouploadeddocumentlink.ApprovalRejectionToUploadedDocumentLinkRequestParametersWrapper
import models.uploadeddocument.factories.UploadedDocumentFactories

object ApprovalRejectionToUploadedDocumentLinkFactory {
    fun ofContractDefault(params: ApprovalRejectionToUploadedDocumentLinkRequestParametersWrapper): ApprovalRejectionToUploadedDocumentLink {
        return ApprovalRejectionToUploadedDocumentLink().also {
            it.description = params.description
            it.uploadedDocument = params.uploadedDocument?.let {
                UploadedDocumentFactories.defaultCreate.create(it)
            }
        }
    }

}