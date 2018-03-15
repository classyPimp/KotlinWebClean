package composers.contract

import models.contract.Contract
import models.contract.ContractRequestParametersWrapper
import models.contract.ContractValidator
import models.contract.daos.ContractDaos
import models.contract.factories.ContractFactories
import orm.services.ModelInvalidException
import orm.utils.TransactionRunner
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class CreateComposer(val params: IParam) : ComposerBase() {

    lateinit var onSuccess: (Contract)->Unit
    lateinit var onError: (Contract)->Unit

    lateinit var contractToCreate: Contract
    lateinit var wrappedParams: ContractRequestParametersWrapper

    override fun beforeCompose(){
        wrapParams()
        buildContractToCreate()
        preloadRequiredForValidationAndReturningToClient()
        validate()
    }

    fun wrapParams() {
        params.get("contract")?.let {
            wrappedParams = ContractRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    fun buildContractToCreate() {
        contractToCreate = ContractFactories.default.create(wrappedParams)
    }

    fun preloadRequiredForValidationAndReturningToClient() {
        ContractDaos.show.preloadRequiredForValidationOnCreate(contractToCreate)
    }

    fun validate() {
        ContractValidator(contractToCreate).createScenario()
        if (!contractToCreate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        TransactionRunner.run {
            val contractStatus = contractToCreate.contractStatus!!
            contractStatus.record.save(it.inTransactionDsl)
            contractToCreate.contractStatusId = contractStatus.id

            contractToCreate.record.saveCascade(it.inTransactionDsl)
        }
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidException -> {
                onError(
                        contractToCreate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractToCreate)
    }

}

