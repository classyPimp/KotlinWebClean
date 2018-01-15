package controllers.persontocounterpartylinks.persontocounterpartylinktouploadeddocumentlinks

import composers.persontocounterpartylinks.PersonToCounterPartyLinksComposers
import controllers.BaseController
import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import models.persontocounterpartylinktouploadeddocumentlink.daos.PersonToCounterPartyLinkToUploadedDocumentLinkDaos
import models.persontocounterpartylinktouploadeddocumentlink.tojsonserializers.PersonToCounterPartyLinkToUploadedDocumentLinkSerializers
import router.src.ServletRequestContext

class PersonToCounterPartyLinkToUploadedDocumentLinksController(context: ServletRequestContext) : BaseController(context) {

    fun index(){
        val links = PersonToCounterPartyLinkToUploadedDocumentLinkDaos.index.default()

        renderJson(
                PersonToCounterPartyLinkToUploadedDocumentLinkSerializers.index.onSuccess(links)
        )
    }

    fun create(){
        val params = requestParams()
        val personToCounterPartyLinkId = context.routeParameters.get("personToCounterPartyLinkId")?.toLongOrNull()

        val composer = PersonToCounterPartyLinksComposers.PersonToCounterPartyLinkToUploadedDocumentLinks.create(params, personToCounterPartyLinkId)



        composer.run()

    }

}