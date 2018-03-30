package models.usertouserrolelink

import models.usertouserrolelink.UserToUserRoleLink
import utils.requestparameters.IParam

import java.sql.Timestamp

import org.jooq.generated.tables.Users
import models.user.User
import models.user.UserRequestParametersWrapper
import org.jooq.generated.tables.UserRoles
import models.userrole.UserRole
import models.userrole.UserRoleRequestParametersWrapper

class UserToUserRoleLinkRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val userId: Long? by lazy { requestParameters.get("userId")?.long }
    val userRoleId: Long? by lazy { requestParameters.get("userRoleId")?.long }
    val user: UserRequestParametersWrapper? by lazy {
        requestParameters.get("user")?.let {
            UserRequestParametersWrapper(it)
        }
    }
    val userRole: UserRoleRequestParametersWrapper? by lazy {
        requestParameters.get("userRole")?.let {
            UserRoleRequestParametersWrapper(it)
        }
    }


}