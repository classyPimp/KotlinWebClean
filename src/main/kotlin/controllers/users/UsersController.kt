package controllers.users

import composers.users.Create
import composers.users.UsersComposers
import controllers.ApplicationControllerBase
import models.avatar.Avatar
import models.user.User
import models.user.daos.UserDaos
import models.user.tojsonserializers.UserSerializers
import orm.avatargeneratedrepository.AvatarRecord
import orm.usergeneratedrepository.UserToJsonSerializer
import router.src.ServletRequestContext

class UsersController(requestContext: ServletRequestContext): ApplicationControllerBase(requestContext) {

    fun create(){
        UsersComposers.create(requestParams()).let {
            it.onSuccess = { user ->

                session.let {
                    it.logIn(user.id!!)
                    it.commit()
                }

                renderJson(
                        UserSerializers.create.onSuccess(user)
                )
            }

            it.onFail = { user ->
                renderJson(
                        UserSerializers.create.onError(user)
                )
            }

            it.run()
        }
    }

    fun forSearchFormIndex() {
        val query = requestQueryStringParams().get("query")
        val users = UserDaos.index.byQuery(query)
        renderJson(
                UserSerializers.forSearchFormIndex.onSuccess(users)
        )
    }

}