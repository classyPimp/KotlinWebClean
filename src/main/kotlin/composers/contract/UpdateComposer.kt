package composers.contract

import models.contract.Contract
import models.contract.ContractRequestParametersWrapper
import models.contract.ContractValidator
import models.contract.daos.ContractDaos
import models.contract.updaters.ContractUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class UpdateComposer(val params: IParam, val id: Long?) : ComposerBase() {

    lateinit var onSuccess: (Contract)->Unit
    lateinit var onError: (Contract)->Unit

    lateinit var contractToUpdate: Contract
    lateinit var wrappedParams: ContractRequestParametersWrapper

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError())
        wrapParams()
        findAndSetContractToUpdate()
        runUpdater()
        preloadRequired()
        validate()
    }

    private fun wrapParams() {
        params.get("contract")?.let {
            wrappedParams = ContractRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun findAndSetContractToUpdate() {
        ContractDaos.show.forManageEdit(id!!)?.let {
            contractToUpdate = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun runUpdater() {
        ContractUpdaters.default.update(contractToUpdate, wrappedParams)
    }

    private fun preloadRequired() {
        contractToUpdate.record.loadContractCategory()
    }

    private fun validate() {
        ContractValidator(contractToUpdate).manageUpdateScenario()
        if (!contractToUpdate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
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
                            it.record.validationManager.addGeneralError("no such contract")
                        }
                )
            }
            is ModelInvalidError -> {
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

