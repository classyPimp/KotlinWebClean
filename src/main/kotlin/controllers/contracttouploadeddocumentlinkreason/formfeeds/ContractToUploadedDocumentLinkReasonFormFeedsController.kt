package controllers.contracttouploadeddocumentlinkreason.formfeeds

import controllers.BaseController
import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason
import models.contracttouploadeddocumentlinkreason.daos.ContractToUploadedDocumentLinkReasonDaos
import models.contracttouploadeddocumentlinkreason.tojsonserializers.ContractToUploadedDocumentLinkReasonSerializers
import router.src.ServletRequestContext

class ContractToUploadedDocumentLinkReasonFormFeedsController(context: ServletRequestContext) : BaseController(context) {

    fun index() {
        val contractToUploadedDocumentLinkReasons = ContractToUploadedDocumentLinkReasonDaos.index.forFormFeedsIndex()

        renderJson(
                ContractToUploadedDocumentLinkReasonSerializers.FormFeeds.index.onSuccess(contractToUploadedDocumentLinkReasons)
        )
    }

}