package models.contractcategory.tojsonserializers

import models.contractcategory.ContractCategory
import orm.contractcategorygeneratedrepository.ContractCategoryToJsonSerializer

object ShowSerializer {

    fun onSuccess(contractCategory: ContractCategory): String {
        ContractCategoryToJsonSerializer(contractCategory).let {

            return it.serializeToString()
        }
    }

    fun onError(contractCategory: ContractCategory): String {
        ContractCategoryToJsonSerializer(contractCategory). let {


            it.includeErrors()
            return it.serializeToString()
        }
    }

}