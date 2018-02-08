package composers.contract.contracttocounterpartylink

import models.contract.Contract
import models.contracttocounterpartylink.ContractToCounterPartyLink
import models.contracttocounterpartylink.ContractToCounterPartyLinkRequestParametersWrapper
import utils.composer.ComposerBase
import utils.composer.composerexceptions.UnprocessableEntryError
import utils.requestparameters.IParam

class ContractContractToCounterPartyLinkCreateComposer(
        val params: IParam,
        val contractId: Long?
) : ComposerBase() {

    lateinit var onSuccess: (ContractToCounterPartyLink)->Unit
    lateinit var onError: (ContractToCounterPartyLink)->Unit

    lateinit var contractToCounterPartyLinkToCreate: ContractToCounterPartyLink
    lateinit var wrappedParams: ContractToCounterPartyLinkRequestParametersWrapper
    lateinit var contract: Contract

    override fun beforeCompose(){
        contractId ?: failImmediately(UnprocessableEntryError())
        wrapParams()
        build()
        assignContract()
        validate()
    }

    private fun wrapParams() {

    }

    private fun build() {

    }

    private fun assignContract() {

    }

    private fun validate() {

    }

    override fun compose(){

    }

    override fun fail(error: Throwable) {
        when(error) {

            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractToCounterPartyLinkToCreate)
    }

}

