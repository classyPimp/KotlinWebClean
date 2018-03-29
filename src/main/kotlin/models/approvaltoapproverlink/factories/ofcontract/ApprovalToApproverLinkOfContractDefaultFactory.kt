package models.approvaltoapproverlink.factories.ofcontract

import models.approvaltoapproverlink.ApprovalToApproverLink
import models.approvaltoapproverlink.ApprovalToApproverLinkRequestParametersWrapper
import models.user.factories.UserFactories

object ApprovalToApproverLinkOfContractDefaultFactory {

    fun create(params: MutableList<ApprovalToApproverLinkRequestParametersWrapper>?): MutableList<ApprovalToApproverLink>? {
        return params?.mapTo(
                mutableListOf<ApprovalToApproverLink>()
        ) {
            ApprovalToApproverLink().also { model ->
                model.userId = it.userId
                it.user?.let {
                    model.user = UserFactories.create.ofApprovalOfContractCreate(it)
                }
            }
        }
    }

}