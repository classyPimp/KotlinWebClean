package composers.persons

import models.person.Person
import models.person.PersonValidator
import models.person.factories.PersonFactories
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.InvalidRequestParametersError
import utils.requestparameters.IParam

class Create(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (Person)->Unit
    lateinit var onError: (Person)->Unit

    lateinit var personBeingCreated: Person
    lateinit var personParams: IParam

    override fun beforeCompose(){
        params.get("person")?.let {
            personParams = it
        } ?: failImmediately(InvalidRequestParametersError())

        constructPerson()
        validate()
    }

    private fun constructPerson() {
        personBeingCreated = PersonFactories.create(personParams)
    }

    private fun validate() {
        PersonValidator(personBeingCreated).createScenario()
    }

    override fun compose(){
        personBeingCreated.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is InvalidRequestParametersError -> {
                Person().let {
                    onError(
                        it.also {
                            it.record.validationManager.addError("general", "unprocessable entry")
                        }
                    )
                }
            }
            is ModelInvalidError -> {
                onError(personBeingCreated)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(personBeingCreated)
    }

}

