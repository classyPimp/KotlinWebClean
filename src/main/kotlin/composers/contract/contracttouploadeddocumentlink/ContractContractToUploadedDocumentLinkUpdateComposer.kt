package composers.contract.contracttouploadeddocumentlink

import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLinkRequestParametersWrapper
import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLinkValidator
import models.contracttouploadeddocumentlink.daos.ContractToUploadedDocumentLinkDaos
import models.contracttouploadeddocumentlink.updaters.ContractToUploadedDocumentLinkUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
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
        contractId ?: failImmediately(BadRequestError())
        id ?: failImmediately(BadRequestError())
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
            failImmediately(ModelInvalidError())
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
            is ModelInvalidError -> {
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

