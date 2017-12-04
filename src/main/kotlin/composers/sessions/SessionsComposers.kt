package composers.sessions

import utils.requestparameters.IParam

/**
 * Created by Муса on 27.11.2017.
 */
object SessionsComposers {

    fun create(params: IParam): Create {
        return Create(params)
    }

}