package models.contracttocounterpartylink.daos

import org.jooq.generated.tables.ContractToCounterPartyLinks
import orm.contracttocounterpartylinkgeneratedrepository.ContractToCounterPartyLinkRecord
import models.contracttocounterpartylink.ContractToCounterPartyLink
import org.jooq.generated.Tables.CONTRACT_TO_COUNTER_PARTY_LINKS

object ContractToCounterPartyLinkIndexDao {

    val table = CONTRACT_TO_COUNTER_PARTY_LINKS

    fun forContract(contractId: Long): MutableList<ContractToCounterPartyLink> {
        return ContractToCounterPartyLinkRecord.GET()
                .preload {
                    it.counterParty() {
                        it.preload {
                            it.incorporationForm()
                        }
                    }
                }
                .where(table.CONTRACT_ID.eq(contractId))
                .execute()
    }

}