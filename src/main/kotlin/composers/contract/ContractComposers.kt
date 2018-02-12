package composers.contract

import composers.contract.contracttocounterpartylink.ContractContractToCounterPartyLinkCreateComposer
import composers.contract.contracttocounterpartylink.ContractContractToCounterPartyLinkDestroyComposer
import composers.contract.contracttocounterpartylink.ContractContractToCounterPartyLinkReplaceComposer
import composers.contract.contracttocounterpartylink.ContractContractToCounterPartylinkUpdateComposer
import utils.requestparameters.IParam

/**
 * Created by Муса on 06.02.2018.
 */
object ContractComposers {

    object ContractToCounterPartyLink {
        fun replace(contractId: Long?, id: Long?, counterPartyIdToReplaceWith: Long?): ContractContractToCounterPartyLinkReplaceComposer {
            return ContractContractToCounterPartyLinkReplaceComposer(
                    contractId,
                    id,
                    counterPartyIdToReplaceWith
            )
        }

        fun destroy(contractId: Long?, id: Long?): ContractContractToCounterPartyLinkDestroyComposer {
            return ContractContractToCounterPartyLinkDestroyComposer(contractId, id)
        }

        fun create(contractId: Long?, params: IParam): ContractContractToCounterPartyLinkCreateComposer {
            return ContractContractToCounterPartyLinkCreateComposer(contractId, params)
        }

        fun update(contractId: Long?, id: Long?, params: IParam): ContractContractToCounterPartylinkUpdateComposer {
            return ContractContractToCounterPartylinkUpdateComposer(contractId, id, params)
        }
    }

    fun create(params: IParam): CreateComposer {
        return CreateComposer(params)
    }

    fun update(params: IParam, id: Long?): UpdateComposer {
        return UpdateComposer(params, id)
    }

    fun destroy(id: Long?): DestroyComposer {
        return DestroyComposer(id)
    }

}