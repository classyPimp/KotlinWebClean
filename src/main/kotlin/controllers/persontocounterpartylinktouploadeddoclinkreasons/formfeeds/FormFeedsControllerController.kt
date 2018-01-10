package controllers.persontocounterpartylinktouploadeddoclinkreasons.formfeeds

import controllers.BaseController
import models.persontocounterpartylinktouploadeddoclinkreason.daos.PersonToCounterPartyLinkToUploadedDocLinkReasonDaos
import models.persontocounterpartylinktouploadeddoclinkreason.tojsonserializers.PersonToCounterPartyLinkToUploadedDocLinkReasonSerializers
import router.src.ServletRequestContext

class FormFeedsControllerController(context: ServletRequestContext) : BaseController(context) {

    fun index() {
        val linkReasons = PersonToCounterPartyLinkToUploadedDocLinkReasonDaos.index.forFormFeeds()

        renderJson(
                PersonToCounterPartyLinkToUploadedDocLinkReasonSerializers.FormFeeds.index.onSuccess(linkReasons)
        )
    }

}