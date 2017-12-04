package models.user.factories

import models.account.factories.AccountFactories
import models.user.User
import utils.requestparameters.IParam

object CreateFactory {

    fun create(params: IParam): User {
        val user = User()
        user.name = params.get("name")?.string
        params.get("account")?.let {
            user.account = AccountFactories.create.create(it)
        }
        params.get("avatar")?.let {

        }
        return user
    }

}