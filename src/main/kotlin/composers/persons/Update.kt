package composers.persons

import models.person.Person
import models.person.PersonValidator
import org.jooq.generated.Tables.PEOPLE
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.persongeneratedrepository.PersonRecord
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam
import utils.stdlibextensions.trimAndSquishWhiteSpace

class Update(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (Person)->Unit
    lateinit var onError: (Person)->Unit

    lateinit var personBeingUpdate: Person
    lateinit var personParams: IParam

    override fun beforeCompose(){
        if (id == null) {
            failImmediately(UnprocessableEntryError("route param id is null"))
        }

        params.get("person")?.let {
            personParams = it
       } ?: failImmediately(UnprocessableEntryError("no params under 'person' given"))

        PersonRecord.GET().where(PEOPLE.ID.eq(id!!)).execute().firstOrNull()?.let {
            personBeingUpdate = it
        } ?: failImmediately(ModelNotFoundError("person with id: ${id} not found"))

        updateFields()
        validate()
    }

    private fun updateFields() {
        personBeingUpdate.record.let {
            it.name = personParams.get("name")?.string?.trimAndSquishWhiteSpace()
        }
    }

    private fun validate() {
        PersonValidator(personBeingUpdate).udpateScenario()
    }

    override fun compose(){
        personBeingUpdate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is UnprocessableEntryError -> {
                Person().let {
                    onError(it.also {it.record.validationManager.addGeneralError("unprocessable entry")})
                }
            }

            is ModelNotFoundError -> {
                Person().let {
                    onError(it.also {it.record.validationManager.addGeneralError("no such person")})
                }
            }

            is ModelInvalidException -> {
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

