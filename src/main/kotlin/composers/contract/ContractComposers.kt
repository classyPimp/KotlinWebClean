package composers.contract

import utils.requestparameters.IParam

/**
 * Created by Муса on 06.02.2018.
 */
object ContractComposers {

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