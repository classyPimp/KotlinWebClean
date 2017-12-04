package composers.sessions

import models.account.Account
import models.user.User
import org.jooq.generated.tables.Users.USERS
import orm.usergeneratedrepository.UserSelectQueryBuilder
import utils.composer.ComposerBase
import utils.requestparameters.IParam
import utils.security.PasswrodHashingService
import java.lang.Exception

/**
 * Created by Муса on 27.11.2017.
 */
class Create(
        val params: IParam
): ComposerBase() {

    lateinit var onSuccess: (User)->Unit
    lateinit var onError: (User)->Unit

    var user = User().also {
        it.account = Account()
    }
    var userToLogin: User? = null

    var name: String? = null
    var password: String? = null

    override fun beforeCompose() {
        setNameAndPassword()
        validateInputs()
        findUser()
        testPassword()
    }

    private fun setNameAndPassword() {
        params.get("user")?.let {
            name = it.get("name")?.string
            password = it.get("account")?.get("password")?.string
        }
    }

    class InvalidInputs : Throwable("invalid inputs")

    private fun validateInputs() {
        var hasErrors = false

        if (name.isNullOrBlank()) {
            user.record.validationManager.addNameError("no name provided")
            hasErrors = true
        }
        if (password.isNullOrBlank()) {
            user.account!!.record.validationManager.addPasswordError("no password provided")
            hasErrors = true
        }

        if (hasErrors) {
            failImmediately(InvalidInputs())
        }
    }

    class RecordNotFound : Throwable()

    fun findUser(){
        userToLogin = UserSelectQueryBuilder().where(USERS.NAME.eq(name!!))
                .preload {
                    it.account()
                }
                .limit(1)
                .execute()
                .firstOrNull()

        if (userToLogin == null) {
            failImmediately(RecordNotFound())
        }
    }

    class PasswordInvalid : Throwable()

    fun testPassword(){
        userToLogin!!.let {
            if (!PasswrodHashingService().checkPassword(password!!, it.account!!.password!!)) {
                failImmediately(PasswordInvalid())
            }
        }
    }

    override fun compose() {
        //throw NotImplementedError()
    }

    override fun fail(error: Throwable) {
        println("faile: ${error}")
        when (error) {
            is PasswordInvalid -> {
                user.record.validationManager.addError("general", "user name or password invalid")
                onError.invoke(user)
            }
            is RecordNotFound -> {
                user.record.validationManager.addError("general", "user name or password invalid")
                onError.invoke(user)
            }
            is InvalidInputs -> {
                onError.invoke(user)
            } else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(userToLogin!!)
    }

}