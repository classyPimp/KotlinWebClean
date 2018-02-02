package models.contractcategory.tojsonserializers

import models.contractcategory.ContractCategory
import orm.contractcategorygeneratedrepository.ContractCategoryToJsonSerializer

object IndexSerializer {

    fun onSuccess(contractCategories: MutableList<ContractCategory>): String {
        return ContractCategoryToJsonSerializer.serialize(contractCategories).toString()
    }

}