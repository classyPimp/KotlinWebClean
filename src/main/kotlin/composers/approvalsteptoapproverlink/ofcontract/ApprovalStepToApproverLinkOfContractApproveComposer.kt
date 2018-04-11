package composers.approvalsteptoapproverlink.ofcontract

import models.approval.Approval
import models.approval.daos.ApprovalDaos
import utils.composer.ComposerBase
import models.approvalsteptoapproverlink.ApprovalStepToApproverLink
import models.approvalsteptoapproverlink.ApprovalStepToApproverLinkValidator
import models.approvalsteptoapproverlink.updaters.ApprovalStepToApproverLinkUpdaters
import models.approvaltoapproverlink.ApprovalToApproverLink
import models.approvaltoapproverlink.updaters.ApprovalToApproverLinkUpdaters
import models.contract.Contract
import models.contract.daos.ContractDaos
import org.jooq.DSLContext
import orm.modelUtils.exceptions.ModelNotFoundError
import orm.services.ModelInvalidError
import orm.utils.TransactionRunner
import permissionsystem.CurrentUserUnauthorizedError
import utils.composer.composerexceptions.BadRequestError
import utils.currentuser.ICurrentUser
import java.sql.Timestamp
import java.time.Instant

class ApprovalStepToApproverLinkOfContractApproveComposer(
        val approvalStepToApproverLinkId: Long?,
        val currentUser: ICurrentUser
) : ComposerBase() {

    lateinit var onSuccess: (ApprovalStepToApproverLink)->Unit
    lateinit var onError: (ApprovalStepToApproverLink)->Unit

    lateinit var approvalStepToApproverLinkToApprove: ApprovalStepToApproverLink
    lateinit var approvalToApproverLinkToApprove: ApprovalToApproverLink
    lateinit var approval: Approval

    override fun beforeCompose(){
        approvalStepToApproverLinkId ?: failImmediately(BadRequestError())
        checkIfUserIsLoggedInAndThrowIfNot()
        findAndSetApproval()
        extractAndSetApprovalStepToApproverLinkToApprove()
        extractAndSetApprovalToApproverLinkToApprove()
        validateIfApprovalStepIsLastStepThrowIfNot()
        checkIfUserIsAuthorizedAndThrowIfNot()
        checkIfAlreadyApprovedAndThrowIfSo()
        update()
        validate()
    }

    private fun checkIfUserIsLoggedInAndThrowIfNot() {
        if (!currentUser.isLoggedIn()) {
            failImmediately(CurrentUserUnauthorizedError())
        }
    }

    private fun findAndSetApproval() {
        ApprovalDaos.show.byApprovalStepForApprovalStepToApprovalLinkOfContractApprove(approvalStepToApproverLinkId!!)?.let {
            approval = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun extractAndSetApprovalStepToApproverLinkToApprove() {
        approval.approvalSteps!!.firstOrNull()?.let {
            it.approvalStepToApproverLinks!!.find {
                it.userId == currentUser.userModel!!.id!!
            }?.let {
                approvalStepToApproverLinkToApprove = it
            }
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun extractAndSetApprovalToApproverLinkToApprove() {
        approval.approvalToApproverLinks?.find {
            it.userId == currentUser.userModel!!.id!!
        }?.let {
            approvalToApproverLinkToApprove = it
        } ?: failImmediately(ModelNotFoundError())
    }

    private fun validateIfApprovalStepIsLastStepThrowIfNot() {
        val lastStepId = approval.lastStageId!!
        if (approvalStepToApproverLinkToApprove.approvalStepId!! != lastStepId) {
            throw IllegalStateException()
        }
    }

    private fun checkIfUserIsAuthorizedAndThrowIfNot() {
        if (currentUser.userModel!!.id != approvalStepToApproverLinkToApprove.userId!!) {
            failImmediately(CurrentUserUnauthorizedError())
        }
    }

    private fun checkIfAlreadyApprovedAndThrowIfSo() {
        if (approvalToApproverLinkToApprove.isApproved != null) {
            throw IllegalStateException()
        }
        if (approvalStepToApproverLinkToApprove.isApproved != null) {
            throw IllegalStateException()
        }
    }

    private fun update() {
        ApprovalStepToApproverLinkUpdaters.OfContract.default.whenApproved(approvalStepToApproverLinkToApprove)
        ApprovalToApproverLinkUpdaters.OfContract.default.whenApproved(approvalToApproverLinkToApprove)
    }


    private fun validate() {
        ApprovalStepToApproverLinkValidator(approvalStepToApproverLinkToApprove)
        if (!approvalStepToApproverLinkToApprove.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }


    override fun compose(){
        TransactionRunner.run {
            val tx = it.inTransactionDsl
            checkIfApprovedByAllIfSoMarkContractAsApproved(tx)
            approvalStepToApproverLinkToApprove.record.save(tx)
            approvalStepToApproverLinkToApprove.record.save(tx)
            markCurrentUsersApprovalRejectionsAsFulfilled(tx)
        }
    }

    private fun markCurrentUsersApprovalRejectionsAsFulfilled(transactionContext: DSLContext) {
        val now = Timestamp(Instant.now().toEpochMilli())
        approval.approvalRejections?.filter {
            it.userId == currentUser.userModel!!.id
        }?.forEach {
            it.record.fullfilled = now
            it.record.save(transactionContext)
        }
    }

    private fun checkIfApprovedByAllIfSoMarkContractAsApproved(transactionContext: DSLContext) {
        if (isApprovedByAll()) {
            val contract = findContractToBeMarkedAsApproved()
            markContractAsApproved(contract, transactionContext)
        }
    }

    private fun isApprovedByAll(): Boolean {
        var approvedByAll = true
        approval.approvalToApproverLinks?.find {
            it.isApproved == null
        }?.let {
            approvedByAll = false
        }
        return approvedByAll
    }

    private fun findContractToBeMarkedAsApproved(): Contract {
        ContractDaos.show.byIdForMarkingAsApproved(approval.approvableId!!)?.let {
            return it
        } ?: throw IllegalStateException()
    }

    private fun markContractAsApproved(contract: Contract, transactionContext: DSLContext) {
        contract.contractStatus!!.record.also{
            it.pendingApproval = null
            it.isApproved = Timestamp(Instant.now().toEpochMilli())
            it.save(transactionContext)
        }

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

