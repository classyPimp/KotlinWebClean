package models.userrole

import models.userrole.UserRole
import utils.requestparameters.IParam

import java.sql.Timestamp

import org.jooq.generated.tables.Users
import models.user.User
import models.user.UserRequestParametersWrapper

class UserRoleRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val isSpecific: Boolean? by lazy { requestParameters.get("isSpecific")?.boolean }
    val specificToType: String? by lazy { requestParameters.get("specificToType")?.string }
    val specificToId: Long? by lazy { requestParameters.get("specificToId")?.long }
    val name: String? by lazy { requestParameters.get("name")?.string }
    val userId: Long? by lazy { requestParameters.get("userId")?.long }
    val user: UserRequestParametersWrapper? by lazy {
        requestParameters.get("user")?.let {
            UserRequestParametersWrapper(it)
        }
    }


}