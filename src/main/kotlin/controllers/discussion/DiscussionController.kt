package controllers.discussion

import controllers.ApplicationControllerBase
import models.discussion.daos.DiscussionDaos
import models.discussion.tojsonserializers.DiscussionSerializers
import models.discussionmessage.tojsonserializers.DiscussionMessageSerializers
import models.user.daos.UserDaos
import router.src.ServletRequestContext

class DiscussionController(context: ServletRequestContext) : ApplicationControllerBase(context) {

    fun show() {
        val discussionId = routeParams().get("discussionId")?.toLongOrNull()
        val discussion = DiscussionDaos.show.forShow(discussionId)
        if (discussion == null) {
            sendError(404)
        }
        val discussionMessagesAuthors = UserDaos.index.indexAuthorsOfTheseDiscussionMessages(discussion!!.discussionMessages)

        renderJson(
                DiscussionSerializers.create.onSuccess(discussion, discussionMessagesAuthors)
        )
    }

}