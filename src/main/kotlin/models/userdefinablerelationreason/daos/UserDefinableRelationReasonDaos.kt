package models.userdefinablerelationreason.daos

import org.jooq.generated.tables.UserDefinableRelationReasons
import models.userdefinablerelationreason.daos.UserDefinableRelationReasonShowDao
import models.userdefinablerelationreason.daos.UserDefinableRelationReasonIndexDao
import models.userdefinablerelationreason.daos.UserDefinableRelationReasonEditDao
import models.userdefinablerelationreason.daos.UserDefinableRelationReasonUpdateDao
import models.userdefinablerelationreason.daos.UserDefinableRelationReasonDestroyDao

object UserDefinableRelationReasonDaos {

    val show = UserDefinableRelationReasonShowDao

    val index = UserDefinableRelationReasonIndexDao

    val edit = UserDefinableRelationReasonEditDao

    val update = UserDefinableRelationReasonUpdateDao

    val destroy = UserDefinableRelationReasonDestroyDao

}