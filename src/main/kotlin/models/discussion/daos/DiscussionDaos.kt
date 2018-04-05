package models.discussion.daos

import org.jooq.generated.tables.Discussions
import models.discussion.daos.DiscussionShowDao
import models.discussion.daos.DiscussionIndexDao
import models.discussion.daos.DiscussionEditDao
import models.discussion.daos.DiscussionUpdateDao
import models.discussion.daos.DiscussionDestroyDao

object DiscussionDaos {

    val show = DiscussionShowDao

    val index = DiscussionIndexDao

    val edit = DiscussionEditDao

    val update = DiscussionUpdateDao

    val destroy = DiscussionDestroyDao

}