package models.userdefinablelinkreason.daos

import org.jooq.generated.tables.UserDefinableLinkReasons

object UserDefinableLinkReasonDaos {

    val show = UserDefinableLinkReasonShowDao

    val index = UserDefinableLinkReasonIndexDao

    val edit = UserDefinableLinkReasonEditDao

    val update = UserDefinableLinkReasonUpdateDao

    val destroy = UserDefinableLinkReasonDestroyDao

}