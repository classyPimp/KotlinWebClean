package composers.contract.monetaryobligation

import utils.requestparameters.IParam

/**
 * Created by Муса on 19.02.2018.
 */
object ContractMonetaryObligationComposers {

    fun create(contractId: Long?, params: IParam): ContractMonetaryObligationCreateComposer {
        return ContractMonetaryObligationCreateComposer(contractId, params)
    }

    fun update(id: Long?, params: IParam): ContractMonetaryObligationUpdateComposer {
        return ContractMonetaryObligationUpdateComposer(id, params)
    }

    fun destroy(contractId: Long?, id: Long?): ContractMonetaryObligationDestroyComposer {
        return ContractMonetaryObligationDestroyComposer(contractId, id)
    }

}