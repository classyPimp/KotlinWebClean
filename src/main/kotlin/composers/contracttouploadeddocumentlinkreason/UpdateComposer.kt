package composers.contracttouploadeddocumentlinkreason

import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason
import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReasonRequestParametersWrapper
import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReasonValidator
import models.contracttouploadeddocumentlinkreason.daos.ContractToUploadedDocumentLinkReasonDaos
import models.contracttouploadeddocumentlinkreason.updaters.ContractToUploadedDocumentLinkReasonUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class UpdateComposer(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (ContractToUploadedDocumentLinkReason)->Unit
    lateinit var onError: (ContractToUploadedDocumentLinkReason)->Unit

    lateinit var contractToUploadedDocumentLinkReasonToUpdate: ContractToUploadedDocumentLinkReason
    lateinit var wrappedParams: ContractToUploadedDocumentLinkReasonRequestParametersWrapper

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError())
        findAndSetContractToUploadedDocumentLinkReasonToUpdate()
        wrapParams()
        runUpdater()
        validate()
    }

    private fun findAndSetContractToUploadedDocumentLinkReasonToUpdate() {
        ContractToUploadedDocumentLinkReasonDaos.show.forUpdate(id = id!!)?.let {
            contractToUploadedDocumentLinkReasonToUpdate = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun wrapParams() {
        params.get("contractToUploadedDocumentLinkReason")?.let {
            wrappedParams = ContractToUploadedDocumentLinkReasonRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun runUpdater() {
        ContractToUploadedDocumentLinkReasonUpdaters.default.update(contractToUploadedDocumentLinkReasonToUpdate, wrappedParams)
    }

    private fun validate() {
        ContractToUploadedDocumentLinkReasonValidator(contractToUploadedDocumentLinkReasonToUpdate).updateScenario()
        if (!contractToUploadedDocumentLinkReasonToUpdate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        contractToUploadedDocumentLinkReasonToUpdate.record.save()
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
            is ModelInvalidError -> {
                onError(
                        contractToUploadedDocumentLinkReasonToUpdate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractToUploadedDocumentLinkReasonToUpdate)
    }

}

