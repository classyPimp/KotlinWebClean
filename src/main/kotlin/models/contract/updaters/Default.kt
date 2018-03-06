package models.contract.updaters

import models.contract.Contract
import models.contract.ContractRequestParametersWrapper


object Default {

    fun update(model: Contract, params: ContractRequestParametersWrapper) {
        model.record.let {
            it.description = params.description
            it.contractCategoryId = params.contractCategoryId
        }
    }

    fun updateGeneralInfo(model: Contract, params: ContractRequestParametersWrapper) {
        model.record.let {
            it.description = params.description
            it.contractCategoryId = params.contractCategoryId
        }
    }

}