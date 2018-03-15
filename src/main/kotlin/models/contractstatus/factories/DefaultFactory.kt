package models.contractstatus.factories

import models.contractstatus.ContractStatus
import models.contractstatus.daos.ContractStatusDaos

object DefaultFactory {
    fun createForContractCreate(): ContractStatus {
        return ContractStatus().also {
            it.isProject = true
            generateAndAssignContractNumber(it)
        }
    }

    private fun generateAndAssignContractNumber(contractStatus: ContractStatus) {
        val generatedInternalNumber = ContractStatus.generateNumber()
        val existingWithSuchInternalNumber = ContractStatusDaos.show.byInternalNumber(generatedInternalNumber)
        if (existingWithSuchInternalNumber == null) {
            contractStatus.internalNumber = generatedInternalNumber
        } else {
            generateAndAssignContractNumber(contractStatus)
        }

    }

}