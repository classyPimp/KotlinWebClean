package utils.currentuser

import models.user.User
import models.user.daos.UserDaos
import models.userrole.UserRole
import orm.usergeneratedrepository.UserRecord
import orm.userrolegeneratedrepository.UserRoleToJsonSerializer
import utils.sessions.JwtSessionHandler

/**
 * Created by Муса on 30.03.2018.
 */
class JWTBasedCurrentUser(val session: JwtSessionHandler): ICurrentUser {

    override var userModel: User? = null
        get() {
            if (field != null) {
                return field
            }
            if (isLoggedIn()) {
                field = User().also {
                    it.id = session.getInt("id")?.toLong()
                    it.userRoles = userRolesListInSession
                }
                return field
            }
            return null
        }
        set(value) {
            field = value
        }

    override var userModelStoredInDb: User? = null
        get() {
            if (field != null) {
                return field
            }
            if (isLoggedIn()) {
                field =  UserDaos.show.byId(session.getInt("id")?.toLong()!!)
                return field
            }
            return null
        }
        set(value) {
            field = value
        }

    override val userRolesListInSession: MutableList<UserRole>?
        get() {
            return session.decodedJwt?.getClaim("userRoles")?.asString()?.let {
                val tree = App.component.jacksonObjectMapper().readTree(it)
                tree.mapTo(mutableListOf<UserRole>()) {node ->
                    UserRole().also {
                        it.name = node["name"]?.asText()
                    }
                }
                null
            }
        }

    fun roleListToString(roleListToSerialize: MutableList<UserRole>?): String? {
        return roleListToSerialize?.let {
            UserRoleToJsonSerializer.serialize(it) {
                it.only {
                    arrayOf(it.name)
                }
            }.toString()
        }
    }

    override fun isLoggedIn(): Boolean {
        return session.isLoggedIn()
    }

    override fun logIn(user: User) {
        session.logIn(user.id!!)
        session.addToSession("name", user.name)
        val userRoles = user.userToUserRoleLinks?.mapTo(mutableListOf<UserRole>()) {
            it.userRole!!
        }
        if (userRoles != null) {
            val arrayNode = App.component.jacksonObjectMapper().createArrayNode()
            for (userRole in userRoles) {
                if (userRole.isSpecific != null ) {
                    continue
                }
                val objectNode = App.component.jacksonObjectMapper().createObjectNode()
                objectNode.put("name", userRole.name)
                arrayNode.add(objectNode)
            }
            session.addToSession("userRoles", arrayNode.toString())
        }
        session.commit(60 * 60 * 24 * 365)
    }

    override fun logOut() {
        session.logOut()
    }

    override fun storeRoleInSession(role: UserRole) {
        val roleList = userRolesListInSession ?: mutableListOf<UserRole>()
        roleList.add(role)
        session.addToSession("userRoles", roleListToString(roleList))
        session.commit(60 * 60 * 24 * 365)
    }

    override fun storeRolesInSession(roles: MutableList<UserRole>) {
        val roleList = userRolesListInSession ?: mutableListOf<UserRole>()
        roleList.addAll(roles)
        session.addToSession("userRoles", roleListToString(roleList))
        session.commit(60 * 60 * 24 * 365)
    }

    override fun hasRole(roleName: String): Boolean {
        userRolesListInSession?.let {
            for (userRole in it) {
                if (userRole.name == roleName) {
                    return true
                }
            }
            return false
        } ?: return false
    }


}