package permissionsystem

import utils.currentuser.ICurrentUser

/**
 * Created by Муса on 30.03.2018.
 */
object ApprovalOfContractPermissions {

    fun isAuthorizedToCreate(currentUser: ICurrentUser): Boolean {
        return currentUser.isLoggedIn()
    }

}