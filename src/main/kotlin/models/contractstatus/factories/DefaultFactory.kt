package models.contractstatus.factories

import models.contractstatus.ContractStatus

object DefaultFactory {
    fun createForContractCreate(): ContractStatus {
        return ContractStatus().also {
            it.isProject = true
        }
    }

}