package models.counterparty.daos

import org.jooq.generated.tables.CounterParties
import orm.counterpartygeneratedrepository.CounterPartyRecord
import models.counterparty.CounterParty
import org.jooq.generated.Tables.COUNTER_PARTIES

object CounterPartyShowDao {

    fun getById(id: Long): CounterParty? {
        return CounterPartyRecord.GET().where(
                COUNTER_PARTIES.ID.eq(id)
        )
                .preload {
                    it.incorporationForm()
                }
                .execute().firstOrNull()
    }



}