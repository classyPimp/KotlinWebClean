package controllers.persontocounterpartylinks

import composers.persontocounterpartylinks.PersonToCounterPartyLinksComposers
import controllers.BaseController
import controllers.persontocounterpartylinks.persontocounterpartylinktouploadeddocumentlinks.PersonToCounterPartyLinkToUploadedDocumentLinksController
import models.persontocounterpartylink.PersonToCounterPartyLink
import models.persontocounterpartylink.daos.PersonToCounterPartyLinkDaos
import models.persontocounterpartylink.tojsonserializers.PersonToCounterPartyLinkSerializers
import orm.persontocounterpartylinkgeneratedrepository.PersonToCounterPartyLinkRecord
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class PersonToCounterPartyLinksController(context: ServletRequestContext) : BaseController(context) {

    companion object {
        fun personToCounterPartyLinkToUploadedDocumentLinks(context: ServletRequestContext): PersonToCounterPartyLinkToUploadedDocumentLinksController {
            return PersonToCounterPartyLinkToUploadedDocumentLinksController(context)
        }
    }

    fun create() {
        val composer = PersonToCounterPartyLinksComposers.create(requestParams())

        composer.onSuccess = {
            renderJson(
                    PersonToCounterPartyLinkSerializers.create.onSuccess(it)
            )
        }

        composer.onError = {
            renderJson(
                    PersonToCounterPartyLinkSerializers.create.onError(it)
            )
        }

        composer.run()
    }

    fun show(){
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (id == null) {
            context.response.sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val link: PersonToCounterPartyLink? = PersonToCounterPartyLinkDaos.show.getByIdforShow(id)

        if (link == null) {
            context.response.sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                PersonToCounterPartyLinkSerializers.show.onSuccess(link)
        )

    }

    fun index() {
        val links: MutableList<PersonToCounterPartyLink> = PersonToCounterPartyLinkDaos.index.forIndex()

        renderJson(
                PersonToCounterPartyLinkSerializers.index.onSuccess(links)
        )
    }

    fun indexForPerson() {
        val personId = routeParams().get("personId")?.toLongOrNull()
        if (personId == null) {
            sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }
        val personToCounterPartyLinks = PersonToCounterPartyLinkDaos.index.forPerson(personId)

        renderJson(
                PersonToCounterPartyLinkSerializers.indexForPerson.onSuccess(personToCounterPartyLinks)
        )
    }

    fun edit() {
        val id = context.routeParameters.get("id")?.toLongOrNull()

        if (id == null) {
            context.response.sendError(SC_INTERNAL_SERVER_ERROR)
            return
        }

        val link: PersonToCounterPartyLink? = PersonToCounterPartyLinkDaos.edit.getByIdForEdit(id)

        if (link == null) {
            context.response.sendError(SC_NOT_FOUND)
            return
        }

        renderJson(
                PersonToCounterPartyLinkSerializers.edit.onSuccess(link)
        )
    }

    fun destroy() {
        val composer = PersonToCounterPartyLinksComposers.destroy(context.routeParameters.get("id")?.toLongOrNull())

        composer.onSuccess = {
            renderJson(
                    PersonToCounterPartyLinkSerializers.destroy.onSuccess(it)
            )
        }

        composer.onError = {
            renderJson(
                    PersonToCounterPartyLinkSerializers.destroy.onError(it)
            )
        }

        composer.run()
    }

    fun update() {
        val composer = PersonToCounterPartyLinksComposers.update(
                requestParams(),
                context.routeParameters.get("id")?.toLongOrNull()
        )

        composer.onSuccess = {
            renderJson(
                    PersonToCounterPartyLinkSerializers.update.onSuccess(it)
            )
        }

        composer.onError = {
            renderJson(
                    PersonToCounterPartyLinkSerializers.update.onError(it)
            )
        }

        composer.run()
    }

}