package models.contract.updaters

import models.contract.Contract
import models.contract.ContractRequestParametersWrapper
import models.contractstatus.updaters.ContractStatusUpdaters


object Default {

    fun update(model: Contract, params: ContractRequestParametersWrapper) {
        model.record.let {
            it.description = params.description
            it.contractCategoryId = params.contractCategoryId
        }
        ContractStatusUpdaters.default.ofContractGeneralInfoUpdate(model.contractStatus!!, params.contractStatus!!)
    }

    fun updateGeneralInfo(model: Contract, params: ContractRequestParametersWrapper) {
        model.record.let {
            it.description = params.description
            it.contractCategoryId = params.contractCategoryId
        }
    }

}