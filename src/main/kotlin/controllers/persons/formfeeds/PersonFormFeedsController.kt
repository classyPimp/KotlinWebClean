package controllers.persons.formfeeds

import controllers.BaseController
import models.person.tojsonserializers.PersonSerializers
import orm.persongeneratedrepository.PersonRecord
import router.src.ServletRequestContext

/**
 * Created by Муса on 04.01.2018.
 */
class PersonFormFeedsController(context: ServletRequestContext) : BaseController(context) {

    fun index() {
        val persons = PersonRecord.GET().execute()
        renderJson(
                PersonSerializers.FormFeeds.index.onSuccess(persons)
        )
    }

}