package models.contractcategory.tojsonserializers

import models.contractcategory.ContractCategory
import orm.contractcategorygeneratedrepository.ContractCategoryToJsonSerializer

object FormFeedsIndex {

    fun onSuccess(contractCategories: MutableList<ContractCategory>): String {
        return ContractCategoryToJsonSerializer.serialize(contractCategories).toString()
    }

}