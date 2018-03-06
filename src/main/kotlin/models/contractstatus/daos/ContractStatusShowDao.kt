package models.contractstatus.daos

import org.jooq.generated.tables.ContractStatuses
import orm.contractstatusgeneratedrepository.ContractStatusRecord
import models.contractstatus.ContractStatus
import org.jooq.generated.Tables.CONTRACTS
import org.jooq.generated.Tables.CONTRACT_STATUSES

object ContractStatusShowDao {

    val table = CONTRACT_STATUSES

    fun forContract(contractId: Long?): ContractStatus? {
        return ContractStatusRecord.GET()
                .join {
                    it.contract()
                }
                .where(CONTRACTS.ID.eq(contractId))
                .limit(1)
                .execute()
                .first()
    }

    fun forContractEdit(contractId: Long?): ContractStatus? {
        return ContractStatusRecord.GET()
                .join {
                    it.contract()
                }
                .where(CONTRACTS.ID.eq(contractId))
                .limit(1)
                .execute()
                .first()
    }

    fun forContractUpdate(id: Long): ContractStatus? {
        return ContractStatusRecord.GET()
                .where(table.ID.eq(id))
                .limit(1)
                .execute()
                .firstOrNull()
    }

}