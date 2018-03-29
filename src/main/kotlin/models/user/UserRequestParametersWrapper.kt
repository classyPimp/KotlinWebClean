package models.user

import models.user.User
import utils.requestparameters.IParam

class UserRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long() }
    val name: String? by lazy { requestParameters.get("name")?.string }

}