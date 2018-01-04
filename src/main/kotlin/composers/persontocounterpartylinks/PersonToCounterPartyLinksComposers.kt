package composers.persontocounterpartylinks

import utils.requestparameters.IParam

/**
 * Created by Муса on 04.01.2018.
 */
object PersonToCounterPartyLinksComposers {

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