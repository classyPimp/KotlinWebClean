package models.contract.daos

import org.jooq.generated.tables.Contracts
import orm.contractgeneratedrepository.ContractRecord
import models.contract.Contract

object ContractIndexDao {
    fun forIndex(): MutableList<Contract> {
        return ContractRecord.GET()
                .preload {
                    it.contractToCounterPartyLinks() {
                        it.preload {
                            it.counterParty() {
                                it.preload {
                                    it.incorporationForm()
                                }
                            }
                        }
                    }
                    it.contractNumber()
                    it.contractStatus()
                    it.contractCategory()
                }
                .execute()
    }


}