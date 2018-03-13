package models.contractnumber.factories

import models.contractnumber.ContractNumber
import models.contractnumber.daos.ContractNumberDaos
import orm.contractnumbergeneratedrepository.ContractNumberRecord

object ContractNumberDefaultFactory {

    fun ofContractCreate(): ContractNumber {
        val contractNumber = ContractNumber()
        generateAndAssignInternalContractNumber(contractNumber)
        return contractNumber
    }

    private fun generateAndAssignInternalContractNumber(contractNumber: ContractNumber) {
        val generatedInternalNumber = ContractNumber.generateNumber()
        val existingWithSuchInternalNumber = ContractNumberDaos.show.byInternalNumber(generatedInternalNumber)
        if (existingWithSuchInternalNumber == null) {
            contractNumber.internalNumber = generatedInternalNumber
        } else {
            generateAndAssignInternalContractNumber(contractNumber)
        }

    }

}