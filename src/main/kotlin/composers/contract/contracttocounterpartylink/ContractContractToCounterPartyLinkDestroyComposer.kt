package composers.contract.contracttocounterpartylink

import models.contracttocounterpartylink.ContractToCounterPartyLink
import models.contracttocounterpartylink.ContractToCounterPartyLinkValidator
import models.contracttocounterpartylink.daos.ContractToCounterPartyLinkDaos
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError

class ContractContractToCounterPartyLinkDestroyComposer(
        val contractId: Long?,
        val id: Long?
) : ComposerBase() {

    lateinit var onSuccess: (ContractToCounterPartyLink)->Unit
    lateinit var onError: (ContractToCounterPartyLink)->Unit

    lateinit var contractToCounterPartyLinkToDestroy: ContractToCounterPartyLink

    override fun beforeCompose(){
        contractId ?: failImmediately(BadRequestError())
        id ?: failImmediately(BadRequestError())
        findAndSetContractToCounterPartyLinkToDestroy()
        validate()
    }

    private fun findAndSetContractToCounterPartyLinkToDestroy() {
        ContractToCounterPartyLinkDaos.show.forContractDestroy(
                contractId = contractId!!,
                id = id!!)?.let {
            contractToCounterPartyLinkToDestroy = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun validate() {
        ContractToCounterPartyLinkValidator(contractToCounterPartyLinkToDestroy).forContractDestroyScenario()
        if (!contractToCounterPartyLinkToDestroy.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        contractToCounterPartyLinkToDestroy.record.delete()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        ContractToCounterPartyLink().also {
                            it.record.validationManager.addGeneralError("can't delete no such link to counter party")
                        }
                )
            }
            is ModelInvalidError -> {
                onError(
                        contractToCounterPartyLinkToDestroy
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractToCounterPartyLinkToDestroy)
    }

}

