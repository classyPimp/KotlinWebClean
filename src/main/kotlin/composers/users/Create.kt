package composers.users

import models.user.User
import models.user.UserValidator
import models.user.factories.UserFactories
import orm.services.ModelInvalidException
import orm.utils.TransactionRunner
import utils.requestparameters.IParam
import utils.composer.ComposerBase
import utils.security.PasswrodHashingService

/**
 * Created by Муса on 20.11.2017.
 */
class Create(
        val params: IParam
) : ComposerBase() {

    lateinit var onFail: ((User)->Unit)
    lateinit var onSuccess: ((User)->Unit)

    lateinit var user: User

    class NoParamsSupplied : Throwable("no params supplied")

    override fun beforeCompose() {
        build()
        validate()
        if (user.record.validationManager.isValid()) {
            hashPassword()
        }
    }

    fun build() {
        params.get("user").let {
            if (it == null) {
                failImmediately(NoParamsSupplied())
            } else {
                user = UserFactories.create.create(it)
            }
        }
    }


    fun validate() {
        UserValidator(user).createScenario()
    }

    fun hashPassword(){
        user.account?.let {
            it.password?.let { password ->
                it.password = PasswrodHashingService().hashPassword(password)
            }
        }
    }

    override fun compose() {
        TransactionRunner.run { tx ->
            user.record.saveCascade(tx.inTransactionDsl)
        }
        setUnhashedPasswordToAccountToReturnInResponse()
    }

    private fun setUnhashedPasswordToAccountToReturnInResponse() {
        user.account?.let {
            it.password = params.get("user")?.get("account")?.get("password")?.string
        }
    }

    override fun fail(error: Throwable) {
        when (error) {
            is ModelInvalidException -> {
                onFail.invoke(user)
            }
            is NoParamsSupplied -> {
                User().record.let {
                    it.validationManager.addError("general", "invalid entry")
                    onFail.invoke(user)
                }
            }
            else -> {
                throw Exception()
            }
        }
    }

    override fun success() {
        onSuccess.invoke(user)
    }

}