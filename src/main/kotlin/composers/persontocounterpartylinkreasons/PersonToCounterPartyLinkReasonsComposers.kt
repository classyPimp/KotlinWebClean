package composers.persontocounterpartylinkreasons

import utils.requestparameters.IParam

/**
 * Created by Муса on 28.12.2017.
 */
object PersonToCounterPartyLinkReasonsComposers {

    fun create(params: IParam): Create {
        return Create(params)
    }

    fun destroy(id: Long?): Destroy {
        return Destroy(id)
    }

    fun update(params: IParam, id: Long?): Update {
        return Update(params, id)
    }

}