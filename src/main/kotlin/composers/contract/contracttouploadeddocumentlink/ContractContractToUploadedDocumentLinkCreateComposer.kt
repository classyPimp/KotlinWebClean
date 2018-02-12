package composers.contract.contracttouploadeddocumentlink

import models.contract.Contract
import models.contract.daos.ContractDaos
import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLinkRequestParametersWrapper
import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLinkValidator
import models.contracttouploadeddocumentlink.factories.ContractToUploadedDocumentLinkFactories
import models.uploadeddocument.UploadedDocument
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidException
import orm.utils.TransactionRunner
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class ContractContractToUploadedDocumentLinkCreateComposer(
        val contractId: Long?,
        val params: IParam
) : ComposerBase() {

    lateinit var onSuccess: (ContractToUploadedDocumentLink)->Unit
    lateinit var onError: (ContractToUploadedDocumentLink)->Unit

    lateinit var contractToUploadedDocumentLinkToCreate: ContractToUploadedDocumentLink
    lateinit var wrappedParams: ContractToUploadedDocumentLinkRequestParametersWrapper
    var contract: Contract? = null
    var uploadedDocument: UploadedDocument? = null

    override fun beforeCompose(){
        contractId ?: failImmediately(UnprocessableEntryError())
        wrapParams()
        findAndSetContract()
        build()
        assignContractId()
        preloadRequired()
        validate()
    }

    private fun wrapParams() {
        params.get("contractToUploadedDocumentLink")?.let {
            wrappedParams = ContractToUploadedDocumentLinkRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    private fun findAndSetContract() {
        ContractDaos.show.forContractToUploadedDocumentLinkCreate(contractId!!)?.let {
            contract = it
        }
    }

    private fun build() {
        contractToUploadedDocumentLinkToCreate = ContractToUploadedDocumentLinkFactories.default.create(wrappedParams)
        uploadedDocument = contractToUploadedDocumentLinkToCreate.uploadedDocument
    }

    private fun assignContractId() {
        contractToUploadedDocumentLinkToCreate.contractId = contractId
        contractToUploadedDocumentLinkToCreate.contract = contract
    }

    private fun preloadRequired() {
        contractToUploadedDocumentLinkToCreate.record.loadContractToUploadedDocumentLinkReason()
    }

    private fun validate() {
        ContractToUploadedDocumentLinkValidator(contractToUploadedDocumentLinkToCreate).createScenario()
        if (!contractToUploadedDocumentLinkToCreate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        uploadedDocument!!.contractToUploadedDocumentLinks = mutableListOf(contractToUploadedDocumentLinkToCreate)
        TransactionRunner.run {
            uploadedDocument!!.record.saveCascade(it.inTransactionDsl)
            uploadedDocument!!.file.finalizeOperation()
        }
    }

    override fun fail(error: Throwable) {
        uploadedDocument?.file?.finalizeOperation()
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        ContractToUploadedDocumentLink().also {
                            it.record.validationManager.addGeneralError("no such contract")
                        }
                )
            }
            is ModelInvalidException -> {
                onError(
                        contractToUploadedDocumentLinkToCreate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractToUploadedDocumentLinkToCreate)
    }

}

