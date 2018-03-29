package composers.incorporationforms

import models.incorporationform.IncorporationForm
import models.incorporationform.IncorporationFormRequestParametersWrapper
import models.incorporationform.IncorporationFormValidator
import models.incorporationform.factories.IncorporationFormFactories
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
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
        } ?: failImmediately(BadRequestError("no incorporationForm parameters supplied"))
    }

    private fun buildAndSetIncorporationFormBeingCreated() {
        incorporationFormBeingCreated = IncorporationFormFactories.create.create(wrappedParams)
    }

    private fun validate() {
        IncorporationFormValidator(incorporationFormBeingCreated).createScenario()
        if (!incorporationFormBeingCreated.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError("incroporation form invalid"))
        }
    }

    override fun compose(){
        incorporationFormBeingCreated.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
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

