package models.contractnumber.daos

import org.jooq.generated.tables.ContractNumbers
import orm.contractnumbergeneratedrepository.ContractNumberRecord
import models.contractnumber.ContractNumber
import org.jooq.generated.Tables.CONTRACT_NUMBERS

object ContractNumberShowDao {

    val table = CONTRACT_NUMBERS

    fun byInternalNumber(internalNumber: String): ContractNumber? {
        return ContractNumberRecord.GET()
                .where(table.INTERNAL_NUMBER.eq(internalNumber))
                .limit(1)
                .execute()
                .firstOrNull()
    }

}