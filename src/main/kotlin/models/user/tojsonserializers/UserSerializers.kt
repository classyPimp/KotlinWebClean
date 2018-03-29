package models.user.tojsonserializers

import models.user.tojsonserializers.forsearchform.UserForSearchFormIndexSerializer

object UserSerializers {
    val create = Create

    val forSearchFormIndex = UserForSearchFormIndexSerializer

}