package models.user

import models.account.AccountValidator
import models.avatar.AvatarValidator
import orm.usergeneratedrepository.UserValidatorTrait

/**
 * Created by Муса on 20.11.2017.
 */
class UserValidator(model: User) : UserValidatorTrait(model, model.record.validationManager) {

    fun createScenario(): Boolean {
        validateName()
        validateAccount()
        validateAvatar()
        return this.validationManager.isValid()
    }

    private fun validateAvatar() {
        model.avatar?.let {
            testGeneral {
                AvatarValidator(it).createScenario()
            }
        }
    }

//    private fun validateAvatar() {
//        avatarTester().let {
//            test ->
//
//        }
//    }

    private fun validateAccount() {
        accountTester().let {
            test ->
            test.shouldNotBeNull(model.account)
            model.account?.let {
                testGeneral {
                    AccountValidator(it).createScenario()
                }
            }
        }
    }

    private fun validateName() {
        nameTester().let {
            test->
            if (test.shouldNotBeNull(model.name)) {
                model.name?.let {
                    test.shouldBeLongerThan(it, 3)
                }
            }
        }
    }



}