package models.monetaryobligation.daos

import org.jooq.generated.tables.MonetaryObligations
import orm.monetaryobligationgeneratedrepository.MonetaryObligationRecord
import models.monetaryobligation.MonetaryObligation
import org.jooq.generated.Tables.MONETARY_OBLIGATIONS

object MonetaryObligationIndexDao {

    val table = MONETARY_OBLIGATIONS

    fun forContractManage(contractId: Long): MutableList<MonetaryObligation> {
        val monetaryObligations = MonetaryObligationRecord.GET()
                .preload {
                    it.monetaryObligationParts()
                }
                .where(table.CONTRACT_ID.eq(contractId))
                .execute()

        return monetaryObligations
    }


}