package models.user.factories

import models.account.factories.AccountFactories
import models.user.User
import models.user.UserRequestParametersWrapper
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

    fun ofApprovalOfContractCreate(params: UserRequestParametersWrapper): User {
        return User().also {
            it.id = params.id
            it.name = params.name
        }
    }

}