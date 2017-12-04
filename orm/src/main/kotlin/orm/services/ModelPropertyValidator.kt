package orm.services

import sun.security.util.Length


/**
 * Created by classypimp on 10/27/17.
 */
class ModelPropertyValidator(
        private val propertyName: String,
        private val validationManager: ModelValidationManagerTrait
) {

    fun shouldNotBeNull(value: Any?): Boolean {
        if (value == null) {
            validationManager.addError(propertyName, "should not be null")
            return false
        } else {
            return true
        }
    }

    fun shouldBeLongerThan(value: String, minLength: Int): Boolean {
        if (value.length <= minLength) {
            validationManager.addError(propertyName, "too short")
            return false
        } else {
            return true
        }
    }

    //TODO: implement normal validation
    fun shouldBeValidEmail(value: String): Boolean {
        if (!value.contains(' ')) {
            value.split('@').let {
                if (it.size == 2) {
                    it.getOrNull(1)?.let {
                        if (it.contains('.')) {
                            return true
                        }
                    }
                }
            }
        }
        validationManager.addError(propertyName, "invalid email adress")
        return false
    }


}