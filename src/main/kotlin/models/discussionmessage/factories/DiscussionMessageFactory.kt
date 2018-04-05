package models.discussionmessage.factories

import models.discussionmessage.DiscussionMessage
import models.discussionmessage.DiscussionMessageRequestParametersWrapper

object DiscussionMessageFactory {
    fun createDefault(params: DiscussionMessageRequestParametersWrapper, userId: Long, discussionId: Long): DiscussionMessage {
        return DiscussionMessage().also {
            it.text = params.text
            it.discussionId = discussionId
            it.discussionMessageId = params.discussionMessageId
            it.userId = userId
        }
    }
}