package composers.contract.contracttocounterpartylink

import models.contracttocounterpartylink.ContractToCounterPartyLink
import models.contracttocounterpartylink.ContractToCounterPartyLinkValidator
import models.contracttocounterpartylink.daos.ContractToCounterPartyLinkDaos
import models.contracttocounterpartylink.updaters.ContractToCounterPartyLinkUpdaters
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import utils.composer.ComposerBase
import utils.composer.composerexceptions.BadRequestError

class ContractContractToCounterPartyLinkReplaceComposer(
        val contractId: Long?,
        val id: Long?,
        val counterPartyIdToReplaceWith: Long?
) : ComposerBase() {

    lateinit var onSuccess: (ContractToCounterPartyLink)->Unit
    lateinit var onError: (ContractToCounterPartyLink)->Unit

    lateinit var contractToCounterPartyLink: ContractToCounterPartyLink

    override fun beforeCompose(){
        contractId ?: failImmediately(BadRequestError())
        id ?: failImmediately(BadRequestError())
        counterPartyIdToReplaceWith ?: failImmediately(BadRequestError())

        findAndSetContractToCounterPartyLink()
        update()
        preloadNewCounterParty()
        validate()
    }

    private fun findAndSetContractToCounterPartyLink() {
        ContractToCounterPartyLinkDaos.show.forContractReplace(
                id = id!!,
                contractId = contractId!!
        )?.let {
            contractToCounterPartyLink = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun update() {
        ContractToCounterPartyLinkUpdaters.default.updateWhenContractReplace(
                contractToCounterPartyLink,
                counterPartyIdToReplaceWith!!
        )
    }

    private fun preloadNewCounterParty() {
        contractToCounterPartyLink.record.loadCounterParty() {
            it.preload {
                it.incorporationForm()
            }
        }
    }

    private fun validate() {
        ContractToCounterPartyLinkValidator(contractToCounterPartyLink).counterPartyReplaceScenario()
        if (!contractToCounterPartyLink.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    override fun compose(){
        contractToCounterPartyLink.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelNotFoundError -> {
                onError(
                        ContractToCounterPartyLink().also {
                            it.record.validationManager.addGeneralError("no such counter party")
                        }
                )
            }
            is ModelInvalidError -> {
                onError(
                        contractToCounterPartyLink
                )
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(contractToCounterPartyLink)
    }

}

