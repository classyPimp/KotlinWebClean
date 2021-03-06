package composers.approval

import composers.approval.ofcontract.ApprovalOfContractCreateComposer
import utils.currentuser.ICurrentUser
import utils.requestparameters.IParam

/**
 * Created by Муса on 28.03.2018.
 */
object ApprovalComposers {

    object OfContract {

        fun create(contractId: Long?, params: IParam, currentUser: ICurrentUser): ApprovalOfContractCreateComposer {
            return ApprovalOfContractCreateComposer(contractId, params, currentUser)
        }

    }

}