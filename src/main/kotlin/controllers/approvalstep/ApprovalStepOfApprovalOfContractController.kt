package controllers.approvalstep

import composers.approvalstep.ApprovalStepOfApprovalOfContractCreateComposer
import controllers.ApplicationControllerBase
import models.approvalstep.tojsonserializers.ApprovalStepSerializers
import router.src.ServletRequestContext

class ApprovalStepOfApprovalOfContractController(context: ServletRequestContext) : ApplicationControllerBase(context) {

    fun create() {
        val composer = ApprovalStepOfApprovalOfContractCreateComposer(
                approvalId =  routeParams().get("approvalId")?.toLongOrNull(),
                currentUser = currentUser,
                params = requestParams()
        )

        composer.onError = {
            renderJson(
                    ApprovalStepSerializers.ofApprovalStepOfContractCreate.onError(it)
            )
        }

        composer.onSuccess = {
            renderJson(
                    ApprovalStepSerializers.ofApprovalStepOfContractCreate.onSuccess(it)
            )
        }

        composer.run()
    }

}