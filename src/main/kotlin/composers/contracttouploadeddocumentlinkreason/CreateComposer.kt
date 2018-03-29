package composers.contracttouploadeddocumentlinkreason

import composers.contacttypes.Update
import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReason
import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReasonRequestParametersWrapper
import models.contracttouploadeddocumentlinkreason.ContractToUploadedDocumentLinkReasonValidator
import models.contracttouploadeddocumentlinkreason.factories.ContractToUploadedDocumentLinkReasonFactories
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.requestparameters.IParam

class CreateComposer(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (ContractToUploadedDocumentLinkReason)->Unit
    lateinit var onError: (ContractToUploadedDocumentLinkReason)->Unit

    lateinit var contractToUploadedDocumentLinkReasonToCreate: ContractToUploadedDocumentLinkReason
    lateinit var wrappedParams: ContractToUploadedDocumentLinkReasonRequestParametersWrapper

    override fun beforeCompose(){
        wrapParams()
        buildContractToUploadedDocumentLinkReason()
        validate()
    }

    private fun wrapParams() {
        params.get("contractToUploadedDocumentLinkReason")?.let {
            wrappedParams = ContractToUploadedDocumentLinkReasonRequestParametersWrapper(it)
        } ?: failImmediately(Update.UnprocessableEntry())
    }

    private fun buildContractToUploadedDocumentLinkReason() {
        contractToUploadedDocumentLinkReasonToCreate = ContractToUploadedDocumentLinkReasonFactories.default.create(wrappedParams)
    }

    private fun validate() {
        ContractToUploadedDocumentLinkReasonValidator(contractToUploadedDocumentLinkReasonToCreate).createScenario()
        if (!contractToUploadedDocumentLinkReasonToCreate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        contractToUploadedDocumentLinkReasonToCreate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(
                        contractToUploadedDocumentLinkReasonToCreate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractToUploadedDocumentLinkReasonToCreate)
    }

}

