package composers.incorporationforms

import models.incorporationform.IncorporationForm
import models.incorporationform.daos.IncorporationFormDaos
import orm.modelUtils.exceptions.ModelNotFoundError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError

class Destroy(val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (IncorporationForm)->Unit
    lateinit var onError: (IncorporationForm)->Unit

    lateinit var incorporationFormBeingDestroyed: IncorporationForm

    override fun beforeCompose(){
        id ?: failImmediately(UnprocessableEntryError())
        findAndSetIncorporationFormBeingDestroyed()
    }

    private fun findAndSetIncorporationFormBeingDestroyed() {
        IncorporationFormDaos.show.findForShowById(id!!)?.let {
            incorporationFormBeingDestroyed = it
        } ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        incorporationFormBeingDestroyed.record.delete()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        IncorporationForm().also {
                            it.record.validationManager.addGeneralError("can't delete could not be found")
                        }
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(incorporationFormBeingDestroyed)
    }

}

