package composers.contracttouploadeddocumentlinkreason

import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason
import models.contracttouploadeddocumentlinkreason.daos.ContractToUploadedDocumentLinkReasonDaos
import orm.modelUtils.exceptions.ModelNotFoundError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError

class DestroyComposer(val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (ContractToUploadedDocumentLinkReason)->Unit
    lateinit var onError: (ContractToUploadedDocumentLinkReason)->Unit

    lateinit var contractToUploadedDocumentLinkReasonToDestroy: ContractToUploadedDocumentLinkReason

    override fun beforeCompose(){
        id ?: failImmediately(UnprocessableEntryError())
        findAndSetContractToUploadedDocumentLinkReasonToDestroy()
    }

    private fun findAndSetContractToUploadedDocumentLinkReasonToDestroy() {
        ContractToUploadedDocumentLinkReasonDaos.show.forDestroy(id = id!!)?.let {
            contractToUploadedDocumentLinkReasonToDestroy = it
        } ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        contractToUploadedDocumentLinkReasonToDestroy.record.delete()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        ContractToUploadedDocumentLinkReason().also {
                            it.record.validationManager.addGeneralError("no such link reason in database")
                        }
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractToUploadedDocumentLinkReasonToDestroy)
    }

}

