package controllers.persontocounterpartylinkreasons.formfeeds

import controllers.BaseController
import models.persontocounterpartylinkreason.daos.PersonToCounterPartyLinkReasonDaos
import models.persontocounterpartylinkreason.tojsonserializers.PersonToCounterPartyLinkReasonSerializers
import router.src.ServletRequestContext

/**
 * Created by Муса on 04.01.2018.
 */
class FormFeedsController(context: ServletRequestContext): BaseController(context) {

    fun index() {
        val linkReasons = PersonToCounterPartyLinkReasonDaos.index.forFormFeeds()

        renderJson(
                PersonToCounterPartyLinkReasonSerializers.FormFeeds.index.onSuccess(linkReasons)
        )
    }

}