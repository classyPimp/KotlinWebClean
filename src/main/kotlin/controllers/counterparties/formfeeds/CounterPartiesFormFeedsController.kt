package controllers.counterparties.formfeeds

import controllers.BaseController
import models.counterparty.daos.CounterPartyDaos
import models.counterparty.tojsonserializers.CounterPartySerializers
import router.src.ServletRequestContext

/**
 * Created by Муса on 04.01.2018.
 */
class CounterPartiesFormFeedsController(context: ServletRequestContext) : BaseController(context) {

    fun index() {
        val counterParties = CounterPartyDaos.index.forForFormFeeds()

        renderJson(
                CounterPartySerializers.FormFeeds.index.onSuccess(counterParties)
        )
    }

}