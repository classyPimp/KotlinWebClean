package composers.contract.contracttocounterpartylink

import models.contract.Contract
import models.contract.daos.ContractDaos
import models.contracttocounterpartylink.ContractToCounterPartyLink
import models.contracttocounterpartylink.ContractToCounterPartyLinkRequestParametersWrapper
import models.contracttocounterpartylink.ContractToCounterPartyLinkValidator
import models.contracttocounterpartylink.factories.ContractToCounterPartyLinkFactories
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError
import utils.requestparameters.IParam

class ContractContractToCounterPartyLinkCreateComposer(
        val contractId: Long?,
        val params: IParam
) : ComposerBase() {

    lateinit var onSuccess: (ContractToCounterPartyLink)->Unit
    lateinit var onError: (ContractToCounterPartyLink)->Unit

    lateinit var contractToCounterPartyLinkToCreate: ContractToCounterPartyLink
    lateinit var wrappedParams: ContractToCounterPartyLinkRequestParametersWrapper
    lateinit var contract: Contract

    override fun beforeCompose(){
        contractId ?: failImmediately(BadRequestError())
        wrapParams()
        build()
        assignContract()
        preloadCounterPartyForValidation()
        validate()
    }

    private fun wrapParams() {
        params.get("contractToCounterPartyLink")?.let {
            wrappedParams = ContractToCounterPartyLinkRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun build() {
        contractToCounterPartyLinkToCreate = ContractToCounterPartyLinkFactories.default.create(wrappedParams)
    }

    private fun assignContract() {
        val contract = ContractDaos.show.forContractToCounterPartyLinkCreate(contractId!!)
        if (contract != null) {
            contractToCounterPartyLinkToCreate.contractId = contract.id
            contractToCounterPartyLinkToCreate.contract = contract
        }
    }

    private fun preloadCounterPartyForValidation() {
        contractToCounterPartyLinkToCreate.record.loadCounterParty() {
            it.preload {
                it.incorporationForm()
            }
        }
    }

    private fun validate() {
        ContractToCounterPartyLinkValidator(contractToCounterPartyLinkToCreate).createScenario()
        if (!contractToCounterPartyLinkToCreate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        contractToCounterPartyLinkToCreate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(contractToCounterPartyLinkToCreate)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractToCounterPartyLinkToCreate)
    }

}

