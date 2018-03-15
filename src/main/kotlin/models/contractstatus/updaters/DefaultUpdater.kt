package models.contractstatus.updaters

import models.contractstatus.ContractStatus
import models.contractstatus.ContractStatusRequestParametersWrapper


object DefaultUpdater {

    fun update(model: ContractStatus) {

    }

    fun ofContractGeneralInfoUpdate(contractStatus: ContractStatus, params: ContractStatusRequestParametersWrapper) {
        contractStatus.record.also {
            it.assignedNumber = params.assignedNumber
            it.formalDate = params.formalDate
        }
    }

    fun forContractUpdate(model: ContractStatus, params: ContractStatusRequestParametersWrapper) {
        model.record.also {
            it.isCancelled = params.isCancelled
            it.isCommitted = params.isCommitted
            it.isCompleted = params.isCompleted
            it.isCancelled = params.isCancelled
            it.isProject = params.isProject
            it.isSupplement = params.isSupplement
            it.isSupplemented = params.isSupplemented
        }
    }

}