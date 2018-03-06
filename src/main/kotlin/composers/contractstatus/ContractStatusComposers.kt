package composers.contractstatus

import utils.requestparameters.IParam

/**
 * Created by Муса on 06.03.2018.
 */
object ContractStatusComposers {

    fun forContractUpdate(id: Long?, params: IParam): ContractStatusForContractUpdateComposer {
        return ContractStatusForContractUpdateComposer(id, params)
    }

}