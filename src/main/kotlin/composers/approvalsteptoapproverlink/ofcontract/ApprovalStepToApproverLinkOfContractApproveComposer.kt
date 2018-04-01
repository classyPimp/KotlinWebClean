package composers.approvalsteptoapproverlink.ofcontract

import utils.composer.ComposerBase
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import utils.currentuser.ICurrentUser

class ApprovalStepToApproverLinkOfContractApproveComposer(
        val contractId: Long?,
        val userId: Long?,
        val currentUser: ICurrentUser
) : ComposerBase() {

    lateinit var onSuccess: (ApprovalStepToApproverLink)->Unit
    lateinit var onError: (ApprovalStepToApproverLink)->Unit

    lateinit var approvalStepToApproverLinkToApprove: ApprovalStepToApproverLink

    override fun beforeCompose(){
        findAndSetApprovalStepToApproverLinkToApprove()
        checkIfUserIsAuthorized()
        update()
        validate()
    }

    private fun findAndSetApprovalStepToApproverLinkToApprove() {

    }

    private fun checkIfUserIsAuthorized() {

    }

    private fun update() {

    }

    private fun validate() {

    }


    override fun compose(){

    }

    override fun fail(error: Throwable) {
        when(error) {

            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(approvalStepToApproverLinkToApprove)
    }

}

