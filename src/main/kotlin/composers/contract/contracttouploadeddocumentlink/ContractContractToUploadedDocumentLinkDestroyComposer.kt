package composers.contract.contracttouploadeddocumentlink

import models.contracttouploadeddocumentlink.ContractToUploadedDocumentLink
import models.contracttouploadeddocumentlink.daos.ContractToUploadedDocumentLinkDaos
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.utils.TransactionRunner
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError

class ContractContractToUploadedDocumentLinkDestroyComposer(
        val contractId: Long?,
        val id: Long?
) : ComposerBase() {

    lateinit var onSuccess: (ContractToUploadedDocumentLink)->Unit
    lateinit var onError: (ContractToUploadedDocumentLink)->Unit

    lateinit var contractToUploadedDocumentLinkToDestroy: ContractToUploadedDocumentLink

    override fun beforeCompose(){
        contractId ?: failImmediately(BadRequestError())
        id ?: failImmediately(BadRequestError())
        findAndSetContractToUploadedDocumentLinkToDestroy()
    }

    private fun findAndSetContractToUploadedDocumentLinkToDestroy() {
        ContractToUploadedDocumentLinkDaos.show.forDestroy(contractId!!, id!!)?.let {
            contractToUploadedDocumentLinkToDestroy = it
        } ?: failImmediately(ModelNotFoundError())
    }

    override fun compose(){
        TransactionRunner.run {tx ->
            val inTransactionDsl = tx.inTransactionDsl
            val uploadedDocument = contractToUploadedDocumentLinkToDestroy.uploadedDocument!!

            contractToUploadedDocumentLinkToDestroy.record.delete(inTransactionDsl)

            uploadedDocument.record.delete(
                    before = {
                        uploadedDocument.file.delete()
                    },
                    after = {
                        uploadedDocument.file.finalizeOperation()
                    },
                    dslContext = inTransactionDsl
            )
        }
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        ContractToUploadedDocumentLink().also {
                            it.record.validationManager.addGeneralError("can't be deleted, no such link to file")
                        }
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractToUploadedDocumentLinkToDestroy)
    }

}

