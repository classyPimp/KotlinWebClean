package composers.contract.contracttocounterpartylink

import models.contracttocounterpartylink.ContractToCounterPartyLink
import models.contracttocounterpartylink.ContractToCounterPartyLinkRequestParametersWrapper
import models.contracttocounterpartylink.ContractToCounterPartyLinkValidator
import models.contracttocounterpartylink.daos.ContractToCounterPartyLinkDaos
import models.contracttocounterpartylink.updaters.ContractToCounterPartyLinkUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidException
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class ContractContractToCounterPartylinkUpdateComposer(
        val contractId: Long?,
        val id: Long?,
        val params: IParam
) : ComposerBase() {

    lateinit var onSuccess: (ContractToCounterPartyLink)->Unit
    lateinit var onError: (ContractToCounterPartyLink)->Unit

    lateinit var contractToCounterPartyLinkToUpdate: ContractToCounterPartyLink
    lateinit var wrappedParams: ContractToCounterPartyLinkRequestParametersWrapper

    override fun beforeCompose(){
        contractId ?: failImmediately(UnprocessableEntryError())
        id ?: failImmediately(UnprocessableEntryError())
        wrapParams()
        findAndSetContractToCounterPartyLinkToUpdate()
        runUpdater()
        validate()
    }

    private fun wrapParams() {
        params.get("contractToCounterPartyLink")?.let {
            wrappedParams = ContractToCounterPartyLinkRequestParametersWrapper(it)
        } ?: failImmediately(UnprocessableEntryError())
    }

    private fun findAndSetContractToCounterPartyLinkToUpdate() {
        ContractToCounterPartyLinkDaos.show.forUpdate(contractId!!, id!!)?.let {
            contractToCounterPartyLinkToUpdate = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun runUpdater() {
        ContractToCounterPartyLinkUpdaters.default.update(contractToCounterPartyLinkToUpdate, wrappedParams)
    }

    private fun validate() {
        ContractToCounterPartyLinkValidator(contractToCounterPartyLinkToUpdate).updateScenario()
        if (!contractToCounterPartyLinkToUpdate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidException())
        }
    }

    override fun compose(){
        contractToCounterPartyLinkToUpdate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        ContractToCounterPartyLink().also {
                            it.record.validationManager.addGeneralError("no such link")
                        }
                )
            }
            is ModelInvalidException -> {
                onError(
                        contractToCounterPartyLinkToUpdate
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractToCounterPartyLinkToUpdate)
    }

}

