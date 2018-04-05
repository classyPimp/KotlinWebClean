package composers.discussionmessage

import utils.composer.ComposerBase
import models.discussionmessage.DiscussionMessage

class DiscussionMessageOfApprovalRejectionOfContractCreateComposer : ComposerBase() {

    lateinit var onSuccess: (DiscussionMessage)->Unit
    lateinit var onError: (DiscussionMessage)->Unit

    override fun beforeCompose(){

    }

    override fun compose(){

    }

    override fun fail(error: Throwable) {
        when(error) {

            else -> {
                throw error
            }
        }
    }

    override fun success() {
//        onSuccess.invoke()
    }

}

