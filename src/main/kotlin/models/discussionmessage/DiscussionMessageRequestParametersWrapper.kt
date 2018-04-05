package models.discussionmessage

import models.discussionmessage.DiscussionMessage
import utils.requestparameters.IParam

import java.sql.Timestamp

import org.jooq.generated.tables.Discussions
import models.discussion.Discussion
import models.discussion.DiscussionRequestParametersWrapper
import org.jooq.generated.tables.DiscussionMessages
import models.discussionmessage.DiscussionMessageRequestParametersWrapper
import org.jooq.generated.tables.Users
import models.user.User
import models.user.UserRequestParametersWrapper

class DiscussionMessageRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val discussionId: Long? by lazy { requestParameters.get("discussionId")?.long }
    val discussionMessageId: Long? by lazy { requestParameters.get("discussionMessageId")?.long }
    val userId: Long? by lazy { requestParameters.get("userId")?.long }
    val nestLevel: Int? by lazy { requestParameters.get("nestLevel")?.int }
    val text: String? by lazy { requestParameters.get("text")?.string }
    val discussion: DiscussionRequestParametersWrapper? by lazy {
        requestParameters.get("discussion")?.let {
            DiscussionRequestParametersWrapper(it)
        }
    }
    val childMessages: MutableList<DiscussionMessageRequestParametersWrapper>? by lazy {
    requestParameters.get("childMessages")?.paramList()?.let {
        it.mapTo(mutableListOf<DiscussionMessageRequestParametersWrapper>()) {
            DiscussionMessageRequestParametersWrapper(it)
        }
    }
    }
    val parentMessage: DiscussionMessageRequestParametersWrapper? by lazy {
        requestParameters.get("parentMessage")?.let {
            DiscussionMessageRequestParametersWrapper(it)
        }
    }
    val user: UserRequestParametersWrapper? by lazy {
        requestParameters.get("user")?.let {
            UserRequestParametersWrapper(it)
        }
    }


}