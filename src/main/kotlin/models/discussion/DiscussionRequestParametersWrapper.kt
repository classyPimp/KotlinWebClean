package models.discussion

import models.discussion.Discussion
import utils.requestparameters.IParam

import java.sql.Timestamp

import org.jooq.generated.tables.DiscussionMessages
import models.discussionmessage.DiscussionMessage
import models.discussionmessage.DiscussionMessageRequestParametersWrapper

class DiscussionRequestParametersWrapper(val requestParameters: IParam) {

    val id: Long? by lazy { requestParameters.get("id")?.long }
    val createdAt: Timestamp? by lazy { requestParameters.get("createdAt")?.timestamp }
    val updatedAt: Timestamp? by lazy { requestParameters.get("updatedAt")?.timestamp }
    val topic: String? by lazy { requestParameters.get("topic")?.string }
    val userId: Long? by lazy { requestParameters.get("userId")?.long }
    val discussableId: Long? by lazy { requestParameters.get("discussableId")?.long }
    val discussableType: String? by lazy { requestParameters.get("discussableType")?.string }
    val discussionMessages: MutableList<DiscussionMessageRequestParametersWrapper>? by lazy {
    requestParameters.get("discussionMessages")?.paramList()?.let {
        it.mapTo(mutableListOf<DiscussionMessageRequestParametersWrapper>()) {
            DiscussionMessageRequestParametersWrapper(it)
        }
    }
    }


}