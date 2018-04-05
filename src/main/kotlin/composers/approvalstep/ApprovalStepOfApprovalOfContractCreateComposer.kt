package composers.approvalstep

import models.approval.Approval
import models.approval.daos.ApprovalDaos
import utils.composer.ComposerBase
import models.approvalstep.ApprovalStep
import models.approvalstep.ApprovalStepRequestParametersWrapper
import utils.composer.composerexceptions.BadRequestError
import utils.currentuser.ICurrentUser
import utils.requestparameters.IParam

class ApprovalStepOfApprovalOfContractCreateComposer(
        val approvalId: Long?,
        val params: IParam,
        val currentUser: ICurrentUser
) : ComposerBase() {

    lateinit var onSuccess: (ApprovalStep)->Unit
    lateinit var onError: (ApprovalStep)->Unit

    lateinit var approvalStepToCreate: ApprovalStep
    lateinit var approval: Approval
    lateinit var wrappedParams: ApprovalStepRequestParametersWrapper

    override fun beforeCompose(){
        approvalId ?: failImmediately(BadRequestError())
        wrapParams()
        findAndSetApprovalPreloadingRequired()
    }

    private fun wrapParams() {
        params.get("approvalStep")?.let {
            wrappedParams = ApprovalStepRequestParametersWrapper(it)
        } ?: failImmediately(BadRequestError())
    }

    private fun findAndSetApprovalPreloadingRequired() {
        ApprovalDaos.show.ofContractForApprovalStepCreate(approvalId!!).let {

        }
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
       // onSuccess.invoke()
    }

}

