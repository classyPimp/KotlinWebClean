package composers.incorporationforms

import models.incorporationform.IncorporationForm
import models.incorporationform.IncorporationFormRequestParametersWrapper
import models.incorporationform.IncorporationFormValidator
import models.incorporationform.factories.IncorporationFormFactories
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class Create(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (IncorporationForm)->Unit
    lateinit var onError: (IncorporationForm)->Unit

    lateinit var incorporationFormBeingCreated: IncorporationForm
    lateinit var wrappedParams: IncorporationFormRequestParametersWrapper

    override fun beforeCompose(){
        wrapParams()
        buildAndSetIncorporationFormBeingCreated()
        validate()
    }

    private fun wrapParams() {
        params.get("incorporationForm")?.let {
            wrappedParams = IncorporationFormRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError("no incorporationForm parameters supplied"))
    }

    private fun buildAndSetIncorporationFormBeingCreated() {
        incorporationFormBeingCreated = IncorporationFormFactories.create.create(wrappedParams)
    }

    private fun validate() {
        IncorporationFormValidator(incorporationFormBeingCreated).createScenario()
        if (!incorporationFormBeingCreated.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException("incroporation form invalid"))
        }
    }

    override fun compose(){
        incorporationFormBeingCreated.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidException -> {
                onError(incorporationFormBeingCreated)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(incorporationFormBeingCreated)
    }

}

