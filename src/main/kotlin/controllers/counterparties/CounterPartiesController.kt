package controllers.counterparties

import composers.counterparties.CounterPartiesComposers
import controllers.BaseController
import controllers.counterparties.formfeeds.CounterPartiesFormFeedsController
import controllers.counterparties.persontocounterpartylinks.CounterPartiesPersonToCounterPartyLinksController
import models.counterparty.CounterParty
import models.counterparty.daos.CounterPartyDaos
import models.counterparty.tojsonserializers.CounterPartySerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class CounterPartiesController(context: ServletRequestContext) : BaseController(context) {

    companion object {
        fun formFeeds(context: ServletRequestContext): CounterPartiesFormFeedsController {
            return CounterPartiesFormFeedsController(context)
        }

        fun personToCounterPartyLinks(context: ServletRequestContext): CounterPartiesPersonToCounterPartyLinksController {
            return CounterPartiesPersonToCounterPartyLinksController(context)
        }
    }

    fun create(){
        val composer = CounterPartiesComposers.create(requestParams())

        composer.onSuccess = {
            counterParty: CounterParty ->
            renderJson(
                    CounterPartySerializers.create.onSuccess(counterParty)
            )
        }

        composer.onError = {
            counterParty: CounterParty ->
            renderJson(
                    CounterPartySerializers.create.onError(counterParty)
            )
        }

        composer.run()
    }

    fun destroy(){
        val composer = CounterPartiesComposers.destroy(context.routeParameters.get("id")?.toLongOrNull())

        composer.onSuccess = {
            renderJson(
                    CounterPartySerializers.destroy.onSuccess(it)
            )
        }

        composer.onError = {
            renderJson(
                    CounterPartySerializers.destroy.onError(it)
            )
        }

        composer.run()
    }

    fun index() {
        val counterParties = CounterPartyDaos.index.getDefault()
        renderJson(
                CounterPartySerializers.index.onSuccess(counterParties)
        )
    }

    fun show() {
        val id = context.routeParameters.get("id")?.toLongOrNull()
        if (id == null) {
            context.response.sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val counterParty = CounterPartyDaos.show.getById(id)

        if (counterParty == null) {
            context.response.sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                CounterPartySerializers.show.onSuccess(counterParty)
        )
    }

    fun edit() {
        show()
    }

    fun update(){
        println("update")

        val composer = CounterPartiesComposers.update(requestParams(), context.routeParameters.get("id")?.toLongOrNull())

        composer.onSuccess = {
            renderJson(
                CounterPartySerializers.update.onSuccess(it)
            )
        }

        composer.onError = {
            renderJson(
                CounterPartySerializers.update.onError(it)
            )
        }

        composer.run()
    }

}