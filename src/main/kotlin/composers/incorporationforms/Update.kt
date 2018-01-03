package composers.incorporationforms

import models.incorporationform.IncorporationForm
import models.incorporationform.IncorporationFormRequestParametersWrapper
import models.incorporationform.IncorporationFormValidator
import models.incorporationform.daos.IncorporationFormDaos
import models.incorporationform.updaters.IncorporationFormUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class Update(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (IncorporationForm)->Unit
    lateinit var onError: (IncorporationForm)->Unit

    lateinit var incorporationFormBeingUpdate: IncorporationForm
    lateinit var wrappedParams: IncorporationFormRequestParametersWrapper

    override fun beforeCompose(){
        id ?: failImmediately(UnprocessableEntryError("invalid id"))
        findAndSetincorporationFormBeingUpdate()
        wrapParams()
        update()
        validate()
    }

    private fun findAndSetincorporationFormBeingUpdate() {
        IncorporationFormDaos.show.findForShowById(id!!)?.let {
            incorporationFormBeingUpdate = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun wrapParams() {
        params.get("incorporationForm")?.let {
            wrappedParams = IncorporationFormRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError("no params given"))
    }

    private fun update() {
        IncorporationFormUpdaters.defaultUpdater.update(incorporationFormBeingUpdate, wrappedParams)
    }

    private fun validate() {
        IncorporationFormValidator(incorporationFormBeingUpdate).updateScenario()
        if (!incorporationFormBeingUpdate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException(""))
        }
    }

    override fun compose(){
        incorporationFormBeingUpdate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        IncorporationForm().also {
                            it.record.validationManager.addGeneralError("could not be found")
                        }
                )
            }
            is ModelInvalidException -> {
                onError(
                        incorporationFormBeingUpdate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(incorporationFormBeingUpdate)
    }

}

