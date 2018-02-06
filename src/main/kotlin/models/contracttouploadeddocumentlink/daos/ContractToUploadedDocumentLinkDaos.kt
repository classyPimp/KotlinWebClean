package models.contracttouploadeddocumentlink.daos

import org.jooq.generated.tables.ContractToUploadedDocumentLinks
import models.contracttouploadeddocumentlink.daos.ContractToUploadedDocumentLinkShowDao
import models.contracttouploadeddocumentlink.daos.ContractToUploadedDocumentLinkIndexDao
import models.contracttouploadeddocumentlink.daos.ContractToUploadedDocumentLinkEditDao
import models.contracttouploadeddocumentlink.daos.ContractToUploadedDocumentLinkUpdateDao
import models.contracttouploadeddocumentlink.daos.ContractToUploadedDocumentLinkDestroyDao

object ContractToUploadedDocumentLinkDaos {

    val show = ContractToUploadedDocumentLinkShowDao

    val index = ContractToUploadedDocumentLinkIndexDao

    val edit = ContractToUploadedDocumentLinkEditDao

    val update = ContractToUploadedDocumentLinkUpdateDao

    val destroy = ContractToUploadedDocumentLinkDestroyDao

}