package models.discussionmessage.daos

import org.jooq.generated.tables.DiscussionMessages
import models.discussionmessage.daos.DiscussionMessageShowDao
import models.discussionmessage.daos.DiscussionMessageIndexDao
import models.discussionmessage.daos.DiscussionMessageEditDao
import models.discussionmessage.daos.DiscussionMessageUpdateDao
import models.discussionmessage.daos.DiscussionMessageDestroyDao

object DiscussionMessageDaos {

    val show = DiscussionMessageShowDao

    val index = DiscussionMessageIndexDao

    val edit = DiscussionMessageEditDao

    val update = DiscussionMessageUpdateDao

    val destroy = DiscussionMessageDestroyDao

}