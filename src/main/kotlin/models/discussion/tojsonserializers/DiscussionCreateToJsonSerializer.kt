package models.discussion.tojsonserializers

import com.fasterxml.jackson.databind.JsonNode
import models.discussion.Discussion
import models.user.User
import models.user.tojsonserializers.UserSerializers
import orm.discussiongeneratedrepository.DiscussionToJsonSerializer
import orm.services.EmptyToJsonSerializer
import orm.usergeneratedrepository.UserToJsonSerializer

object DiscussionCreateToJsonSerializer {

    fun onSuccess(discussion: Discussion, users: MutableList<User>): String {
        EmptyToJsonSerializer().let {
            it.root.set("discussion", DiscussionToJsonSerializer(discussion).let {
                it.includeDiscussionMessages()
                it.serializeToNode()
            })
            it.root.set("users", UserToJsonSerializer.serialize(users))
            return it.serializeToString()
        }
    }


}