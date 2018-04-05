package composers.approvalsteptoapproverlink.ofcontract

import models.approval.daos.ApprovalDaos
import utils.composer.ComposerBase
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import models.approvalsteptoapproverlink.daos.ApprovalStepToApproverLinkDaos
import models.approvalsteptoapproverlink.updaters.ApprovalStepToApproverLinkUpdaters
import models.approvaltoapproverlink.ApprovalToApproverLink
import models.approvaltoapproverlink.daos.ApprovalToApproverLinkDaos
import models.approvaltoapproverlink.updaters.ApprovalToApproverLinkUpdaters
import org.jooq.DSLContext
import orm.approvalsteptoapproverlinkgeneratedrepository.ApprovalStepToApproverLinkDefaultUpdater
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import orm.utils.TransactionRunner
import permissionsystem.CurrentUserUnauthorizedError
import utils.composer.composerexceptions.BadRequestError
import utils.currentuser.ICurrentUser

class ApprovalStepToApproverLinkOfContractApproveComposer(
        val approvalStepToApproverLinkId: Long?,
        val currentUser: ICurrentUser
) : ComposerBase() {

    lateinit var onSuccess: (ApprovalStepToApproverLink)->Unit
    lateinit var onError: (ApprovalStepToApproverLink)->Unit

    lateinit var approvalStepToApproverLinkToApprove: ApprovalStepToApproverLink
    lateinit var approvalToApproverLinkToApprove: ApprovalToApproverLink

    override fun beforeCompose(){
        approvalStepToApproverLinkId ?: failImmediately(BadRequestError())
        findAndSetApprovalStepToApproverLinkToApprove()
        findAndSetApprovalToApproverLinkToApprove()
        checkIfUserIsAuthorized()
        update()
        //validate()
    }

    private fun findAndSetApprovalStepToApproverLinkToApprove() {
        ApprovalStepToApproverLinkDaos.show.find(id = approvalStepToApproverLinkId!!)?.let {
            approvalStepToApproverLinkToApprove = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun findAndSetApprovalToApproverLinkToApprove() {
        ApprovalToApproverLinkDaos.show.whenApprovedByApprovalStepToApproverLinkId(
                approvalStepToApproverLinkToApprove.id!!,
                approvalStepToApproverLinkToApprove.userId!!
        )?.let {
            approvalToApproverLinkToApprove = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun checkIfUserIsAuthorized() {
        if (!currentUser.isLoggedIn()) {
            failImmediately(CurrentUserUnauthorizedError())
        }
        val userIdOnApprovalStepToApproverLinkToUpdate = approvalStepToApproverLinkToApprove.userId
        println("user check")
        println("${currentUser.userModel!!.id} != ${approvalStepToApproverLinkToApprove.userId}")
        if (currentUser.userModel!!.id != approvalStepToApproverLinkToApprove.userId) {
            failImmediately(CurrentUserUnauthorizedError())
        }
    }

    private fun update() {
        ApprovalStepToApproverLinkUpdaters.OfContract.default.whenApproved(approvalStepToApproverLinkToApprove)
        ApprovalToApproverLinkUpdaters.OfContract.default.whenApproved(approvalToApproverLinkToApprove)
    }


//    private fun validate() {
//
//    }


    override fun compose(){
        TransactionRunner.run {
            val tx = it.inTransactionDsl
            checkIfApprovedByAllIfSoMarkContractAsApproved(tx)
            approvalStepToApproverLinkToApprove.record.save(tx)
            approvalStepToApproverLinkToApprove.record.save(tx)
        }
    }

    private fun checkIfApprovedByAllIfSoMarkContractAsApproved(tx: DSLContext) {
        ApprovalToApproverLinkDaos.index.byApprovalId(approvalToApproverLinkToApprove.approvalId!!)?.let {approvalToApproverLinks ->
            var totalApproved = 0
            approvalToApproverLinks.forEach {
                if (it.isApproved != null) {
                    totalApproved += 1
                }
            }
            //assumed approved by current user
            totalApproved += 1
            if (totalApproved == approvalToApproverLinks.size) {
                markContractAsApproved(tx)
            }
        }
    }

    private fun markContractAsApproved(tx: DSLContext) {
        //TODO: complete this
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(approvalStepToApproverLinkToApprove)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(approvalStepToApproverLinkToApprove)
    }

}

