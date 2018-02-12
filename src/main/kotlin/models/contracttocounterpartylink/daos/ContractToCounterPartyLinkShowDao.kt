package models.contracttocounterpartylink.daos

import org.jooq.generated.tables.ContractToCounterPartyLinks.CONTRACT_TO_COUNTER_PARTY_LINKS
import orm.contracttocounterpartylinkgeneratedrepository.ContractToCounterPartyLinkRecord
import models.contracttocounterpartylink.ContractToCounterPartyLink
import org.jooq.generated.Tables.CONTRACTS
import org.jooq.generated.tables.Contracts

object ContractToCounterPartyLinkShowDao {

    val table = CONTRACT_TO_COUNTER_PARTY_LINKS

    fun forContractReplace(
            id: Long,
            contractId: Long?
    ): ContractToCounterPartyLink? {
        val contractToCounterPartyLink = ContractToCounterPartyLinkRecord.GET()
                .join {
                    it.contract()
                }
                .where(table.ID.eq(id).and(CONTRACTS.ID.eq(contractId)))
                .limit(1)
                .preload {
                    it.contract() {
                        it.preload {
                            it.contractToCounterPartyLinks()
                        }
                    }
                }
                .execute()
                .firstOrNull()
        return contractToCounterPartyLink
    }

    fun forContractDestroy(contractId: Long, id: Long): ContractToCounterPartyLink? {
        val contractToCounterPartyLink = ContractToCounterPartyLinkRecord.GET()
                .where(table.ID.eq(id).and(CONTRACTS.ID.eq(contractId)))
                .join {
                    it.contract()
                }
                .preload {
                    it.contract() {
                        it.preload {
                            it.contractToCounterPartyLinks()
                        }
                    }
                }
                .limit(1)
                .execute()
                .firstOrNull()

        return contractToCounterPartyLink
    }

    fun forUpdate(contractId: Long, id: Long): ContractToCounterPartyLink? {
        val contractToCounterPartyLink = ContractToCounterPartyLinkRecord.GET()
                .where(table.ID.eq(id).and(CONTRACTS.ID.eq(contractId)))
                .join {
                    it.contract()
                }
                .limit(1)
                .execute()
                .firstOrNull()

        return contractToCounterPartyLink
    }


}