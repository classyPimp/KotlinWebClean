package composers.persons

import models.person.Person
import models.person.PersonRequestParametersWrapper
import models.person.PersonValidator
import models.person.updaters.PersonUpdaters
import org.jooq.generated.Tables.PEOPLE
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.persongeneratedrepository.PersonRecord
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class Update(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (Person)->Unit
    lateinit var onError: (Person)->Unit

    lateinit var personBeingUpdate: Person
    lateinit var wrappedPersonParams: PersonRequestParametersWrapper

    override fun beforeCompose(){
        if (id == null) {
            failImmediately(BadRequestError("route param id is null"))
        }

        params.get("person")?.let {
            wrappedPersonParams = PersonRequestParametersWrapper(it)
       } ?: failImmediately(BadRequestError("no params under 'person' given"))

        PersonRecord.GET().where(PEOPLE.ID.eq(id!!)).execute().firstOrNull()?.let {
            personBeingUpdate = it
        } ?: failImmediately(ModelNotFoundError("person with id: ${id} not found"))

        updateProperties()
        validate()
    }

    private fun updateProperties() {
        PersonUpdaters.defaultUpdater.update(personBeingUpdate, wrappedPersonParams)
    }

    private fun validate() {
        PersonValidator(personBeingUpdate).udpateScenario()
    }

    override fun compose(){
        personBeingUpdate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is BadRequestError -> {
                Person().let {
                    onError(it.also {it.record.validationManager.addGeneralError("unprocessable entry")})
                }
            }

            is ModelNotFoundError -> {
                Person().let {
                    onError(it.also {it.record.validationManager.addGeneralError("no such person")})
                }
            }

            is ModelInvalidError -> {
                onError(personBeingUpdate)
            }

            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(personBeingUpdate)
    }

}

