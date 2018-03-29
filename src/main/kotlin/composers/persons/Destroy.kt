package composers.persons

import models.person.Person
import org.jooq.generated.Tables.PEOPLE
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.persongeneratedrepository.PersonRecord
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError

class Destroy(val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (Person)->Unit
    lateinit var onError: (Person)->Unit

    var personToDestroy: Person? = null

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError("no id given as route parameter"))

        id!!.let {
            personToDestroy = PersonRecord.GET().where(PEOPLE.ID.eq(it)).limit(1).execute().firstOrNull()
            personToDestroy ?: failImmediately(ModelNotFoundError("person with id ${id} not found"))
        }
    }

    override fun compose(){
        personToDestroy!!.record.delete()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                Person().let {
                    it.record.validationManager.addGeneralError("no such person")
                    onError(it)
                }
            }
            is BadRequestError -> {
                Person().let {
                    it.record.validationManager.addGeneralError("unprocessable entry")
                    onError(it)
                }
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(personToDestroy!!)
    }

}

