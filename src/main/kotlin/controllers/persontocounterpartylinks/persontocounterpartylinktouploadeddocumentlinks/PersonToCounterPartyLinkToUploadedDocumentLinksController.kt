package controllers.persontocounterpartylinks.persontocounterpartylinktouploadeddocumentlinks

import composers.persontocounterpartylinks.PersonToCounterPartyLinksComposers
import controllers.BaseController
import models.persontocounterpartylinktouploadeddocumentlink.PersonToCounterPartyLinkToUploadedDocumentLink
import models.persontocounterpartylinktouploadeddocumentlink.daos.PersonToCounterPartyLinkToUploadedDocumentLinkDaos
import models.persontocounterpartylinktouploadeddocumentlink.tojsonserializers.PersonToCounterPartyLinkToUploadedDocumentLinkSerializers
import orm.persontocounterpartylinktouploadeddocumentlinkgeneratedrepository.PersonToCounterPartyLinkToUploadedDocumentLinkToJsonSerializer
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

        composer.onSuccess = {
            renderJson(
                    PersonToCounterPartyLinkToUploadedDocumentLinkSerializers.create.onSuccess(it)
            )
        }

        composer.onError = {
            renderJson(
                    PersonToCounterPartyLinkToUploadedDocumentLinkSerializers.create.onError(it)
            )
        }

        composer.run()

    }

    fun destroy() {
        val personToCounterPartyLinkId = context.routeParameters.get("personToCounterPartyLinkId")?.toLongOrNull()
        val id = context.routeParameters.get("id")?.toLongOrNull()

        val composer = PersonToCounterPartyLinksComposers.PersonToCounterPartyLinkToUploadedDocumentLinks.destroy(
                personToCounterPartyLinkId,
                id
        )

        composer.onError = {
            renderJson(
                    PersonToCounterPartyLinkToUploadedDocumentLinkSerializers.destroy.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    PersonToCounterPartyLinkToUploadedDocumentLinkSerializers.destroy.onError(it)
            )
        }

        composer.run()

    }

}