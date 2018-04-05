package controllers.discussionmessage

import composers.discussionmessage.DiscussionMessageCreateComposer
import controllers.ApplicationControllerBase
import models.discussionmessage.tojsonserializers.DiscussionMessageSerializers
import router.src.ServletRequestContext

class DiscussionMessageOfApprovalRejectionOfContractController(context: ServletRequestContext) : ApplicationControllerBase(context) {

    fun create() {
        val discussionId = routeParams().get("discussionId")?.toLongOrNull()
        val composer = DiscussionMessageCreateComposer(discussionId, requestParams(), currentUser)
        composer.onError = {
            renderJson(
                    DiscussionMessageSerializers.create.onError(it)
            )
        }
        composer.onSuccess = {
            renderJson(
                    DiscussionMessageSerializers.create.onError(it)
            )
        }
        composer.run()
    }

}