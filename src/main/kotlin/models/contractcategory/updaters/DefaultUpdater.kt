package models.contractcategory.updaters

import models.contractcategory.ContractCategory
import models.contractcategory.ContractCategoryRequestParametersWrapper


object DefaultUpdater {

    fun update(model: ContractCategory, params: ContractCategoryRequestParametersWrapper) {
        model.record.let {
            it.name = params.name
            it.description = params.description
        }
    }

}