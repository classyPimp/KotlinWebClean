package models.discussionmessage

import models.discussion.daos.DiscussionDaos
import models.discussionmessage.daos.DiscussionMessageDaos
import models.user.daos.UserDaos
import orm.discussionmessagegeneratedrepository.DiscussionMessageValidatorTrait

class DiscussionMessageValidator(model: DiscussionMessage) : DiscussionMessageValidatorTrait(model, model.record.validationManager) {

    fun createScenario(){
        validateText()
        validateDiscussionId()
        validateUserId()
        validateDiscussionMessageId()
    }

    private fun validateUserId() {
        val userId = model.userId
        if (userId == null) {
            throw IllegalStateException()
        }
        if (!UserDaos.show.isExists(userId)) {
            throw IllegalStateException()
        }
    }

    private fun validateText() {
        val text = model.text
        if (text.isNullOrBlank()) {
            validationManager.addTextError("should add text to message")
            return
        }
    }

    private fun validateDiscussionId() {
        val discussionId = model.discussionId
        if (discussionId == null) {
            throw IllegalStateException()
        }
        if (!DiscussionDaos.show.isExists(discussionId)) {
            throw IllegalStateException()
        }
    }

    private fun validateDiscussionMessageId() {
        val discussionMessageId = model.discussionMessageId
        if (discussionMessageId != null) {
            if (!DiscussionMessageDaos.show.isExists(discussionMessageId)) {
                throw IllegalStateException()
            }
        }
    }

}