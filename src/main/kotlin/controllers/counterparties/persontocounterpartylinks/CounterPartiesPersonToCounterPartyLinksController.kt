package controllers.counterparties.persontocounterpartylinks

import controllers.BaseController
import models.persontocounterpartylink.PersonToCounterPartyLink
import models.persontocounterpartylink.daos.PersonToCounterPartyLinkDaos
import models.persontocounterpartylink.tojsonserializers.PersonToCounterPartyLinkSerializers
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR

/**
 * Created by Муса on 04.01.2018.
 */
class CounterPartiesPersonToCounterPartyLinksController(context: ServletRequestContext)
    : BaseController(context) {

    fun index() {
        val counterPartyId = context.routeParameters.get("counterPartyId")?.toLongOrNull()

        println("counterPartyId is")
        println(counterPartyId)

        if (counterPartyId == null) {
            context.response.sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val links: MutableList<PersonToCounterPartyLink> = PersonToCounterPartyLinkDaos
                .index
                .byCounterPartyId(counterPartyId)

        renderJson(
                PersonToCounterPartyLinkSerializers.ForCounterParties
                        .index
                        .onSuccess(links)
        )

    }

}