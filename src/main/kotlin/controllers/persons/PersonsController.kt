package controllers.persons

import composers.persons.PersonsComposers
import controllers.BaseController
import models.person.Person
import models.person.tojsonserializers.PersonSerializers
import org.jooq.generated.Tables.PEOPLE
import orm.persongeneratedrepository.PersonRecord
import orm.persongeneratedrepository.PersonToJsonSerializer
import router.src.ServletRequestContext
import javax.servlet.http.HttpServletResponse

/**
 * Created by Муса on 20.12.2017.
 */
class PersonsController(context: ServletRequestContext) : BaseController(context) {

    fun create() {
        PersonsComposers.create(requestParams()).let {
            it.onSuccess = {
                person: Person ->
                renderJson(
                        PersonSerializers.create.onSuccess(person)
                )
            }

            it.onError = {
                person: Person ->
                renderJson(
                        PersonSerializers.create.onError(person)
                )
            }

            it.run()
        }
    }

    fun get(){
        val id: Long? = context.routeParameters.get("id")?.toLongOrNull()
        var person: Person? = null

        id?.let {
            person = PersonRecord.GET().where(PEOPLE.ID.eq(it)).execute().firstOrNull()
        }

        person?.let {
            renderJson(
                    PersonSerializers.get.onSuccess(it)
            )
        } ?: context.response.sendError(HttpServletResponse.SC_NOT_FOUND)
    }

    fun index() {
        val persons = PersonRecord.GET().execute()
        renderJson(
                PersonSerializers.index.onSuccess(persons)
        )
    }

    fun update(){
        val id = context.routeParameters["id"]?.toLongOrNull()
        val cmpsr = PersonsComposers.update(requestParams(), id)

        cmpsr.onSuccess = {
            person ->
            PersonSerializers.udpate.onSuccess(person).let {
                renderJson(it)
            }

        }

        cmpsr.onError = {
            person ->
            PersonSerializers.udpate.onError(person).let {
                renderJson(it)
            }
        }

        cmpsr.run()
    }

    fun destroy(){
        val id: Long? = context.routeParameters.get("id")?.toLongOrNull()
        val cmpsr = PersonsComposers.destroy(id)
        cmpsr.onSuccess = {
            person: Person ->
            renderJson(
                    PersonSerializers.destroy.onSuccess(person)
            )
        }

        cmpsr.onError = {
            person: Person ->
            renderJson(
                    PersonSerializers.destroy.onError(person)
            )
        }

        cmpsr.run()
    }


}