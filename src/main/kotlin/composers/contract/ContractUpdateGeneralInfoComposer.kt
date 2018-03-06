package composers.contract

import models.contract.Contract
import models.contract.ContractRequestParametersWrapper
import models.contract.ContractValidator
import models.contract.daos.ContractDaos
import models.contract.updaters.ContractUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class ContractUpdateGeneralInfoComposer(
        val contractId: Long?,
        val params: IParam
) : ComposerBase() {

    lateinit var onSuccess: (Contract)->Unit
    lateinit var onError: (Contract)->Unit

    lateinit var contractToUpdate: Contract
    lateinit var wrappedParams: ContractRequestParametersWrapper

    override fun beforeCompose(){
        contractId ?: failImmediately(UnprocessableEntryError())
        findAndSetContractToUpdate()
        wrapParams()
        runUpdater()
        validate()
    }

    private fun findAndSetContractToUpdate() {
        ContractDaos.show.forUpdateGeneralInfo(contractId!!)?.let {
            contractToUpdate = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun wrapParams() {
        params.get("contract")?.let {
            wrappedParams = ContractRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    private fun runUpdater() {
        ContractUpdaters.default.update(contractToUpdate, wrappedParams)
    }

    private fun validate() {
        ContractValidator(contractToUpdate).updateGeneralInfoScenario()
        if (!contractToUpdate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        contractToUpdate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        Contract().also {
                            it.record.validationManager.addGeneralError("not found")
                        }
                )
            }
            is ModelInvalidException -> {
                onError(
                        contractToUpdate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractToUpdate)
    }

}

