package models.avatar

import orm.avatargeneratedrepository.AvatarValidatorTrait

/**
 * Created by Муса on 20.11.2017.
 */
class AvatarValidator(model: Avatar) : AvatarValidatorTrait(model, model.record.validationManager) {

    fun allowedContentTypes(): MutableList<String> {
        return mutableListOf("image/jpeg", "image/png")
    }

    fun createScenario(): Boolean {
        validateFile()
        return validationManager.isValid()
    }

    private fun validateFile(): Boolean {
        model.file.transactionOriginalFile?.let {
            if (it.length() > 5 * 1024 * 1024) {
                validationManager.addError("file", "should be less then 5mb")
                return false
            } else if (!allowedContentTypes().contains(App.component.mimeDetector().detect(it))) {
                validationManager.addError("file", "only image shall be uploaded")
                return false
            }
        }
        return true
    }


}