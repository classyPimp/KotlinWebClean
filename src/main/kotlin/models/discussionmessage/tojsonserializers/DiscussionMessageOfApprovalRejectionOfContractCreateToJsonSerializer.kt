package models.discussionmessage.tojsonserializers

import models.discussionmessage.DiscussionMessage
import orm.discussionmessagegeneratedrepository.DiscussionMessageToJsonSerializer

object DiscussionMessageOfApprovalRejectionOfContractCreateToJsonSerializer {

    fun onSuccess(discussionMessage: DiscussionMessage): String {
        DiscussionMessageToJsonSerializer(discussionMessage).let {

            return it.serializeToString()
        }
    }

    fun onError(discussionMessage: DiscussionMessage): String {
        DiscussionMessageToJsonSerializer(discussionMessage). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}