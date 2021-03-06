package models.approvalsteptouploadeddocumentlink.daos

import org.jooq.generated.tables.ApprovalStepToUploadedDocumentLinks
import models.approvalsteptouploadeddocumentlink.daos.ApprovalStepToUploadedDocumentLinkShowDao
import models.approvalsteptouploadeddocumentlink.daos.ApprovalStepToUploadedDocumentLinkIndexDao
import models.approvalsteptouploadeddocumentlink.daos.ApprovalStepToUploadedDocumentLinkEditDao
import models.approvalsteptouploadeddocumentlink.daos.ApprovalStepToUploadedDocumentLinkUpdateDao
import models.approvalsteptouploadeddocumentlink.daos.ApprovalStepToUploadedDocumentLinkDestroyDao

object ApprovalStepToUploadedDocumentLinkDaos {

    val show = ApprovalStepToUploadedDocumentLinkShowDao

    val index = ApprovalStepToUploadedDocumentLinkIndexDao

    val edit = ApprovalStepToUploadedDocumentLinkEditDao

    val update = ApprovalStepToUploadedDocumentLinkUpdateDao

    val destroy = ApprovalStepToUploadedDocumentLinkDestroyDao

}