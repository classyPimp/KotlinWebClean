package composers.users

import utils.requestparameters.IParam

object UsersComposers {

    fun create(params: IParam): Create {
        return Create(params)
    }

}