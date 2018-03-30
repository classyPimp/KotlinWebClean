package utils.currentuser

import models.user.User
import models.userrole.UserRole

/**
 * Created by Муса on 30.03.2018.
 */
interface ICurrentUser {

    abstract val userRolesListInSession: MutableList<UserRole>?

    abstract fun isLoggedIn(): Boolean

    abstract fun hasRole(roleName: String): Boolean

    abstract var userModel: User?

    abstract var userModelStoredInDb: User?

//    abstract fun hasAnyOfRolesFrom(roleNames: MutableList<String>): Boolean

    abstract fun logOut()

//    abstract fun hasAllRolesWithNames(roleNames: MutableList<String>): Boolean

//    abstract fun listGeneralRoles(): MutableList<String>

//    abstract fun hasRoleSpecificTo(roleName: String, roleSpecificToType: String, specificToId: Long): Boolean

    abstract fun logIn(user: User)

    abstract fun storeRolesInSession(roles: MutableList<UserRole>)

    abstract fun storeRoleInSession(role: UserRole)

}
