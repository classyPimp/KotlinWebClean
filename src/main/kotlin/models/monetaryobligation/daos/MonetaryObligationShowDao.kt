package models.monetaryobligation.daos

import org.jooq.generated.tables.MonetaryObligations
import orm.monetaryobligationgeneratedrepository.MonetaryObligationRecord
import models.monetaryobligation.MonetaryObligation
import org.jooq.generated.Tables.MONETARY_OBLIGATIONS

object MonetaryObligationShowDao {

    val table = MONETARY_OBLIGATIONS

    fun forUpdate(id: Long): MonetaryObligation? {
        val monetaryObligation = MonetaryObligationRecord.GET()
                .preload {
                    it.monetaryObligationParts()
                }
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull()
        return monetaryObligation
    }


}