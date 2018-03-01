package models.counterparty.daos

import org.jooq.generated.tables.CounterParties
import orm.counterpartygeneratedrepository.CounterPartyRecord
import models.counterparty.CounterParty
import org.jooq.generated.Tables.COUNTER_PARTIES
import org.jooq.impl.DSL

object CounterPartyIndexDao {

    val table = COUNTER_PARTIES

    fun getDefault(): MutableList<CounterParty> {
        return CounterPartyRecord.GET()
                .preload {
                    it.incorporationForm()
                }
                .execute()
    }

    fun byQuery(query: String?): MutableList<CounterParty> {
        if (query == null) {
            return CounterPartyRecord.GET()
                    .preload {
                        it.incorporationForm()
                    }
                    .execute()

        }
        return CounterPartyRecord.GET()
                .where(
                        DSL.trueCondition()
                                .and("{0} ~* {1}", table.NAME, DSL.`val`(query))
                )
                .preload {
                    it.incorporationForm()
                }
                .execute()
    }

    fun forForFormFeeds(): MutableList<CounterParty> {
        return CounterPartyRecord.GET()
                .execute()
    }

}