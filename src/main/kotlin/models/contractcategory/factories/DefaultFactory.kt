package models.contractcategory.factories

import models.contractcategory.ContractCategory
import models.contractcategory.ContractCategoryRequestParametersWrapper

object DefaultFactory {
    fun create(wrappedParams: ContractCategoryRequestParametersWrapper): ContractCategory {
        return ContractCategory().also {
            it.name = wrappedParams.name
            it.description = wrappedParams.description
        }
    }


}