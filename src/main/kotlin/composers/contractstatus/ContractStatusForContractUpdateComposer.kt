package composers.contractstatus

import models.contractstatus.ContractStatus
import models.contractstatus.ContractStatusRequestParametersWrapper
import models.contractstatus.ContractStatusValidator
import models.contractstatus.daos.ContractStatusDaos
import models.contractstatus.updaters.ContractStatusUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class ContractStatusForContractUpdateComposer(
        val id: Long?,
        val params: IParam
) : ComposerBase() {

    lateinit var onSuccess: (ContractStatus)->Unit
    lateinit var onError: (ContractStatus)->Unit

    lateinit var contractStatusToUdpate: ContractStatus
    lateinit var wrappedParams: ContractStatusRequestParametersWrapper

    override fun beforeCompose(){
        id ?: failImmediately(BadRequestError())
        findAndSetContractStatusToUpdate()
        wrapParams()
        runUpdater()
        validate()
    }

    private fun findAndSetContractStatusToUpdate() {
        ContractStatusDaos.show.forContractUpdate(id!!)?.let {
            contractStatusToUdpate = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun wrapParams() {
        params.get("contractStatus")?.let {
            wrappedParams = ContractStatusRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun runUpdater() {
        ContractStatusUpdaters.default.forContractUpdate(contractStatusToUdpate, wrappedParams)
    }

    private fun validate() {
        ContractStatusValidator(contractStatusToUdpate).forContractUpdateScenario()
        if (!contractStatusToUdpate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        contractStatusToUdpate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        ContractStatus().also {
                            it.record.validationManager.addGeneralError("not found")
                        }
                )
            }
            is ModelInvalidError -> {
                onError(contractStatusToUdpate)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractStatusToUdpate)
    }
}

