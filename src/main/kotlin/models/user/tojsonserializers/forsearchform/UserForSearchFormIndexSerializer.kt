package models.user.tojsonserializers.forsearchform

import models.user.User
import orm.usergeneratedrepository.UserToJsonSerializer

object UserForSearchFormIndexSerializer {

    fun onSuccess(users: MutableList<User>): String {
        UserToJsonSerializer.serialize(users).let {
            return it.toString()
        }
    }


}