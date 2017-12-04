package models.account

import orm.accountgeneratedrepository.AccountValidatorTrait

/**
 * Created by Муса on 20.11.2017.
 */
class AccountValidator(model: Account) : AccountValidatorTrait(model, model.record.validationManager) {

    fun createScenario(): Boolean {
        validatePassword()
        validateEmail()
        return validationManager.isValid()
    }

    private fun validateEmail() {
        model.email?.let {
            emailTester().let {
                test ->
                test.shouldBeValidEmail(it)
            }
        }
    }

    private fun validatePassword() {
        passwordTester().let {
            test ->
            test.shouldNotBeNull(model.password)
            model.password?.let {
                test("passwordConfirmation", "doesn't match") {
                    (it == model.passwordConfirmation)
                }
            }

        }
    }


}