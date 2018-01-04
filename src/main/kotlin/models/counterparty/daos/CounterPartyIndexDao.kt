package models.counterparty.daos

import org.jooq.generated.tables.CounterParties
import orm.counterpartygeneratedrepository.CounterPartyRecord
import models.counterparty.CounterParty

object CounterPartyIndexDao {

    fun getDefault(): MutableList<CounterParty> {
        return CounterPartyRecord.GET()
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