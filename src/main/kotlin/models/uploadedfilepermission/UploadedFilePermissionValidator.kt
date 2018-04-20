package models.uploadedfilepermission

import orm.uploadedfilepermissiongeneratedrepository.UploadedFilePermissionValidatorTrait

class UploadedFilePermissionValidator(model: UploadedFilePermission) : UploadedFilePermissionValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        //
    }

}