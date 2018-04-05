package models.discussionmessage.tojsonserializers

import models.discussionmessage.DiscussionMessage
import orm.discussionmessagegeneratedrepository.DiscussionMessageToJsonSerializer

object DiscussionMessageCreateToJsonSerializer {

    fun onSuccess(discussionMessage: DiscussionMessage): String {
        DiscussionMessageToJsonSerializer(discussionMessage).let {
            it.includeUser()
            return it.serializeToString()
        }
    }

    fun onError(discussionMessage: DiscussionMessage): String {
        DiscussionMessageToJsonSerializer(discussionMessage). let {
            it.includeUser() {
                it.includeErrors()
            }
            it.includeErrors()
            return it.serializeToString()
        }
    }

}