package composers.counterparties

import utils.requestparameters.IParam

/**
 * Created by Муса on 03.01.2018.
 */
object CounterPartiesComposers {

    fun create(params: IParam): Create {
        return Create(params)
    }

    fun update(params: IParam, id: Long?): Update {
        return Update(params, id)
    }

    fun destroy(id: Long?): Destroy {
        return Destroy(id)
    }


}