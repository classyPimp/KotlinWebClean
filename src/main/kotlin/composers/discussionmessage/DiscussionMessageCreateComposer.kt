package composers.discussionmessage

import utils.composer.ComposerBase
import models.discussionmessage.DiscussionMessage
import models.discussionmessage.DiscussionMessageRequestParametersWrapper
import models.discussionmessage.DiscussionMessageValidator
import models.discussionmessage.factories.DiscussionMessageFactory
import orm.services.ModelInvalidError
import permissionsystem.CurrentUserUnauthorizedError
import utils.composer.composerexceptions.BadRequestError
import utils.currentuser.ICurrentUser
import utils.requestparameters.IParam

class DiscussionMessageCreateComposer(
        val discussionId: Long?,
        val params: IParam,
        val currentUser: ICurrentUser
) : ComposerBase() {

    lateinit var onSuccess: (DiscussionMessage)->Unit
    lateinit var onError: (DiscussionMessage)->Unit

    lateinit var discussionMessageToCreate: DiscussionMessage
    lateinit var wrappedParams: DiscussionMessageRequestParametersWrapper

    override fun beforeCompose(){
        discussionId ?: failImmediately(BadRequestError())
        checkIfUserIsAuthorized()
        wrapParams()
        build()
        preloadRequiredForValidation()
        setNestedLevel()
        validate()
    }

    private fun checkIfUserIsAuthorized() {
        if (!currentUser.isLoggedIn()) {
            failImmediately(CurrentUserUnauthorizedError())
        }
    }

    private fun wrapParams() {
        params.get("discussionMessage")?.let {
            wrappedParams = DiscussionMessageRequestParametersWrapper(it)
        }
    }

    private fun build() {
        discussionMessageToCreate = DiscussionMessageFactory.createDefault(
                params = wrappedParams,
                userId = currentUser.userModel!!.id!!,
                discussionId = discussionId!!
        )
    }

    private fun validate() {
        DiscussionMessageValidator(discussionMessageToCreate).createScenario()
        if (!discussionMessageToCreate.record.validationManager.isValid()) {
            failImmediately(ModelInvalidError())
        }
    }

    private fun preloadRequiredForValidation() {
        discussionMessageToCreate.record.also {
            it.loadParentMessage()
            it.loadUser()
            it.loadDiscussion()
        }
    }

    private fun setNestedLevel() {
        discussionMessageToCreate.also {
            it.parentMessage?.let {parentMessage ->
                it.nestLevel = parentMessage.nestLevel!! + 1
            }
        }
    }

    override fun compose(){
        discussionMessageToCreate.record.save()
    }

    override fun fail(error: Throwable) {
        when(error) {
            is ModelInvalidError -> {
                onError(discussionMessageToCreate)
            }
            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke(discussionMessageToCreate)
    }

}

