package models.account.factories

import models.account.Account
import utils.requestparameters.IParam

object Create {

    fun create(params: IParam): Account {
        val account = Account()
        params.let {
            it.get("password")?.let {
                account.password = it.string
            }
        }
        params.let {
            it.get("passwordConfirmation")?.let {
                account.passwordConfirmation = it.string
            }
        }
        return account
    }

}