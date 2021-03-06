package composers.contract.monetaryobligation

import models.monetaryobligation.MonetaryObligation
import models.monetaryobligation.MonetaryObligationRequestParametersWrapper
import models.monetaryobligation.MonetaryObligationValidator
import models.monetaryobligation.factories.MonetaryObligationFactories
import orm.services.ModelInvalidError
import orm.utils.TransactionRunner
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class ContractMonetaryObligationCreateComposer(val contractId: Long?, val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (MonetaryObligation)->Unit
    lateinit var onError: (MonetaryObligation)->Unit

    lateinit var monetaryObligationToCreate: MonetaryObligation
    lateinit var wrappedParams: MonetaryObligationRequestParametersWrapper

    override fun beforeCompose(){
        contractId ?: failImmediately(BadRequestError())
        wrapParams()
        build()
        validate()
    }

    private fun wrapParams() {
        params.get("monetaryObligation")?.let {
            wrappedParams = MonetaryObligationRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun build() {
        monetaryObligationToCreate = MonetaryObligationFactories.default.create(
                params = wrappedParams,
                contractId = contractId!!
        )
    }

    private fun validate() {
        MonetaryObligationValidator(monetaryObligationToCreate).createScenario()
        if (!monetaryObligationToCreate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        TransactionRunner.run {
            monetaryObligationToCreate.record.saveCascade(it.inTransactionDsl)
        }
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(monetaryObligationToCreate)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(monetaryObligationToCreate)
    }

}

