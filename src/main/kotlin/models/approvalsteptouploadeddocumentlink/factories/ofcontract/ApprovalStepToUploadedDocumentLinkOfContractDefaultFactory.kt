package models.approvalsteptouploadeddocumentlink.factories.ofcontract

import models.approvalsteptouploadeddocumentlink.ApprovalStepToUploadedDocumentLink
import models.approvalsteptouploadeddocumentlink.ApprovalStepToUploadedDocumentLinkRequestParametersWrapper
import models.uploadeddocument.factories.UploadedDocumentFactories
import org.apache.commons.lang3.mutable.Mutable

object ApprovalStepToUploadedDocumentLinkOfContractDefaultFactory {

    fun createList(params: MutableList<ApprovalStepToUploadedDocumentLinkRequestParametersWrapper>): MutableList<ApprovalStepToUploadedDocumentLink> {
        return params.mapTo(
                mutableListOf<ApprovalStepToUploadedDocumentLink>()
        ) {
            ApprovalStepToUploadedDocumentLink().also {model ->
                model.description = it.description
                model.uploadedDocument = it.uploadedDocument?.let {
                    UploadedDocumentFactories.defaultCreate.create(it)
                }
            }
        }
    }

}