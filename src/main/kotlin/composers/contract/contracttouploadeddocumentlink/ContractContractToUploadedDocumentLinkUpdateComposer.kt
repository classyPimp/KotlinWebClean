package composers.contract.contracttouploadeddocumentlink

import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLinkRequestParametersWrapper
import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLinkValidator
import models.contracttouploadeddocumentlink.daos.ContractToUploadedDocumentLinkDaos
import models.contracttouploadeddocumentlink.updaters.ContractToUploadedDocumentLinkUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class ContractContractToUploadedDocumentLinkUpdateComposer(
        val contractId: Long?,
        val id: Long?,
        val params: IParam
) : ComposerBase() {

    lateinit var onSuccess: (ContractToUploadedDocumentLink)->Unit
    lateinit var onError: (ContractToUploadedDocumentLink)->Unit

    lateinit var contractToUploadedDocumentLinkToUpdate: ContractToUploadedDocumentLink
    lateinit var wrappedParams: ContractToUploadedDocumentLinkRequestParametersWrapper

    override fun beforeCompose(){
        contractId ?: failImmediately(UnprocessableEntryError())
        id ?: failImmediately(UnprocessableEntryError())
        wrapParams()
        findAndSetContractToUploadedDocumentLinkToUpdate()
        update()
        validate()
    }

    private fun wrapParams() {
        params.get("contractToUploadedDocumentLink")?.let {
            wrappedParams = ContractToUploadedDocumentLinkRequestParametersWrapper(it)
        }
    }

    private fun findAndSetContractToUploadedDocumentLinkToUpdate() {
        ContractToUploadedDocumentLinkDaos.show.forEdit(contractId = contractId!!, id = id!!)?.let {
            contractToUploadedDocumentLinkToUpdate = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun update() {
        ContractToUploadedDocumentLinkUpdaters.default.update(contractToUploadedDocumentLinkToUpdate, wrappedParams)
    }

    private fun validate() {
        ContractToUploadedDocumentLinkValidator(contractToUploadedDocumentLinkToUpdate).updateScenario()
        if (!contractToUploadedDocumentLinkToUpdate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        contractToUploadedDocumentLinkToUpdate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        ContractToUploadedDocumentLink().also {
                            it.record.validationManager.addGeneralError("not found")
                        }
                )
            }
            is ModelInvalidException -> {
                onError(
                        contractToUploadedDocumentLinkToUpdate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractToUploadedDocumentLinkToUpdate)
    }

}

