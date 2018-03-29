package models.approvaltoapproverlink

import models.user.daos.UserDaos
import orm.approvaltoapproverlinkgeneratedrepository.ApprovalToApproverLinkValidatorTrait

class ApprovalToApproverLinkValidator(model: ApprovalToApproverLink) : ApprovalToApproverLinkValidatorTrait(model, model.record.validationManager) {

    fun ofContractCreateScenario(){
        validateUserId()
    }

    private fun validateUserId() {
        val userId = model.userId
        if (userId == null) {
            validationManager.addUserIdError("user was not selected")
            return
        }
        if (!UserDaos.show.isExists(userId)) {
            validationManager.addUserIdError("no such user")
        }
    }

}